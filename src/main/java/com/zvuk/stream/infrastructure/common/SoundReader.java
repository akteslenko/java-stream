package com.zvuk.stream.infrastructure.common;

import com.zvuk.stream.infrastructure.port.dto.SoundMapDTO;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SoundReader {
    public static final String DIRECTORY = "/files/sounds";

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String VIDEO_CONTENT = "audio/";
    private static final String CONTENT_RANGE = "Content-Range";
    private static final String ACCEPT_RANGES = "Accept-Ranges";
    private static final String BYTES = "bytes";

    private static final int BYTE_RANGE = 1024;
    private static final int STEP_SECONDS = 10;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Prepare the content.
     *
     * @param fileName String.
     * @param fileType String.
     * @param range    String.
     * @return ResponseEntity.
     */
    public ResponseEntity<byte[]> prepareContent(String fileName, String fileType, String range) {
        long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize;
        String fullFileName = fileName + "." + fileType;
        try {
            fileSize = getFileSize(fullFileName);
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                        .header(CONTENT_LENGTH, String.valueOf(fileSize))
                        .body(readByteRange(fullFileName, rangeStart, fileSize - 1)); // Read the object and convert it as bytes
            }
            String[] ranges = range.split("-");
            rangeStart = Integer.parseInt(ranges[0]);
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = fileSize - 1;
            }
            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }
            data = readByteRange(fullFileName, rangeStart, rangeEnd);
        } catch (IOException e) {
            logger.error("Exception while reading the file {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                .header(ACCEPT_RANGES, BYTES)
                .header(CONTENT_LENGTH, contentLength)
                .header(CONTENT_RANGE, BYTES + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);


    }

    /**
     * ready file byte by byte.
     *
     * @param filename String.
     * @param start    long.
     * @param end      long.
     * @return byte array.
     * @throws IOException exception.
     */
    public byte[] readByteRange(String filename, long start, long end) throws IOException {
        Path path = Paths.get(getFilePath(), filename);
        try (InputStream inputStream = (Files.newInputStream(path));
             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[BYTE_RANGE];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        }
    }

    /**
     * Get the filePath.
     *
     * @return String.
     */
    private String getFilePath() {
        URL url = this.getClass().getResource(DIRECTORY);
        return new File(url.getFile()).getAbsolutePath();
    }

    /**
     * Content length.
     *
     * @param fileName String.
     * @return Long.
     */
    public Long getFileSize(String fileName) {
        return Optional.ofNullable(fileName)
                .map(file -> Paths.get(getFilePath(), file))
                .map(this::sizeFromFile)
                .orElse(0L);
    }

    /**
     * Getting the size from the path.
     *
     * @param path Path.
     * @return Long.
     */
    private Long sizeFromFile(Path path) {
        try {
            return Files.size(path);
        } catch (IOException ioException) {
            logger.error("Error while getting the file size", ioException);
        }
        return 0L;
    }

    public SoundMapDTO getSoundMap(String file) throws IOException {
        try {
            Path path = Paths.get(getFilePath(), file);

            FFprobe ffprobe = new FFprobe("ffprobe");
            FFmpegProbeResult probeResult = ffprobe.probe(path.toString());
            FFmpegFormat format = probeResult.getFormat();

            int duration = (int)format.duration;
            int hours = duration / 3600;
            int minutes = (duration % 3600) / 60;
            int seconds = duration % 60;
            List<List<Long>> bytesList = bytesPerSeconds(duration, format.size);

            SoundMapDTO soundMapDTO = new SoundMapDTO();
            soundMapDTO.setBytesList(bytesList);
            soundMapDTO.setMinutes(minutes);
            soundMapDTO.setSeconds(seconds);
            soundMapDTO.setDuration(duration);
            soundMapDTO.setMinutesDuration(String.format("%02d:%02d", minutes, seconds));
            soundMapDTO.setHoursDuration(String.format("%02d:%02d:%02d", hours, minutes, seconds));

            return soundMapDTO;
        } catch (IOException ioException) {
            logger.error("Error while getting sound map.", ioException);
            throw ioException;
        }
    }

    private List<List<Long>> bytesPerSeconds(int seconds, long bytes) {

        int bytesPerSecond = (int)(bytes / seconds);
        int partsCount = seconds / STEP_SECONDS;

        // For future additional information (seconds rages)
//        List<List<Integer>> secondsList = new ArrayList<>();
//        int firstDelaySecond = 0;
//        int secondDelaySecond = STEP_SECONDS;
//        for (int i = 1; i <= partsCount; i++){
//            if(i == partsCount){
//                secondDelaySecond += seconds - secondDelaySecond;
//            }
//            List<Integer> list = new ArrayList<>();
//            list.add(firstDelaySecond);
//            list.add(secondDelaySecond);
//
//            secondDelaySecond = secondDelaySecond + STEP_SECONDS;
//            firstDelaySecond = secondDelaySecond - (STEP_SECONDS - 1);
//
//            secondsList.add(list);
//        }

        long firstDelayBytes = 0;
        long secondDelayBytes = bytesPerSecond * STEP_SECONDS;
        long staticDelayBytes = secondDelayBytes;
        List<List<Long>> bytesList = new ArrayList<>();
        for (int i = 1; i <= partsCount; i++) {
            if(i == partsCount){
                secondDelayBytes += bytes - secondDelayBytes;
            }
            List<Long> list = new ArrayList<>();
            list.add(firstDelayBytes);
            list.add(secondDelayBytes);

            secondDelayBytes = secondDelayBytes + staticDelayBytes;
            firstDelayBytes = secondDelayBytes - (staticDelayBytes - 1);

            bytesList.add(list);
        }

        return bytesList;
    }
}