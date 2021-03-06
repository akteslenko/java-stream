package com.zvuk.stream.infrastructure.services;

import com.zvuk.stream.domain.entities.Track;
import com.zvuk.stream.domain.repositories.TrackRepository;
import com.zvuk.stream.infrastructure.common.SoundReader;
import com.zvuk.stream.infrastructure.port.dto.SoundMapDTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrackService {
    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private SoundReader soundReader;

    @SneakyThrows
    public SoundMapDTO getTrackMapInfo(int trackId, int userId) {

        Track track = trackRepository.findTrackByIdAndUserId(trackId, userId);

        return soundReader.getSoundMap(track);
    }

    @SneakyThrows
    public void saveTrackInfo(MultipartFile file, int userId) {
        String result = soundReader.saveFile(file);

        if (!result.isBlank()) {
            Track track = new Track();
            track.setPath(result);
            track.setName(file.getOriginalFilename());

            SoundMapDTO soundMap = soundReader.getSoundMap(track);
            track.setDuration(soundMap.getDuration());
            track.setFormattedDuration(soundMap.getMinutesDuration());
            track.setFormat(soundMap.getFormat());
            track.setStepSeconds(soundMap.getBytesList().size());
            track.setUserId(userId);

            Track savedTrack = trackRepository.save(track);
        }
    }


    public List<SoundMapDTO> getTracksList(int userId) {
        List<Track> allByUserId = trackRepository.findAllByUserId(userId);
        List<SoundMapDTO> soundMapDTOs = new ArrayList<>();
        allByUserId.forEach(element -> {
            try {
                soundMapDTOs.add(soundReader.getSoundMap(element));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return soundMapDTOs;
    }
}
