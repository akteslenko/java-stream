package com.zvuk.stream.infrastructure.port.http.api;

import com.zvuk.stream.ApiResponse;
import com.zvuk.stream.infrastructure.common.SoundReader;
import com.zvuk.stream.infrastructure.port.dto.SoundMapDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(
        value = "/api/stream/sound/",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class StreamController {

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping(
            value = "chunk",
            method = RequestMethod.GET
    )
    public ResponseEntity<byte[]> getSoundChunk(@RequestHeader Map<String, String> headers) {
        Map<Integer, String> songMap = new HashMap<>();

        songMap.put(0, null);
        songMap.put(1, "0-241750");
        songMap.put(2, "241751-483500");
        songMap.put(3, "483501-725250");
        songMap.put(4, "725251-967000");
        songMap.put(5, "967001-1208750");
        songMap.put(6, "1208751-1450500");
        songMap.put(7, "1450501-1692250");
        songMap.put(8, "1692251-1934000");
        songMap.put(9, "1934001-2175750");
        songMap.put(10, "2175751-2417500");
        songMap.put(11, "2417501-2659250");
        songMap.put(12, "2659251-2901000");
        songMap.put(13, "2901001-3287803");


        Integer index = (headers.containsKey("accept-ranges")) ? Integer.parseInt(headers.get("accept-ranges")) : 0;

        SoundReader soundReader = new SoundReader();
        return soundReader.prepareContent("sound", "mp3", songMap.get(index));
    }

    @RequestMapping(
            value = "map",
            method = RequestMethod.GET
    )
    public ApiResponse getSoundMap() throws IOException {
        SoundReader soundReader = new SoundReader();
        SoundMapDTO soundMap = soundReader.getSoundMap("sound.mp3");

        return ApiResponse.buildResponseObject(HttpStatus.OK, soundMap, null);
    }

}
