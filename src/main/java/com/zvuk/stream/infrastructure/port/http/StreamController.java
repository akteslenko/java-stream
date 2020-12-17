package com.zvuk.stream.infrastructure.port.http;

import com.zvuk.stream.ApiResponse;
import com.zvuk.stream.infrastructure.common.RandomRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        value = "/api/stream/",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class StreamController {

    @Autowired
    ResourceLoader resourceLoader;

    @RequestMapping(
            value = "/map/sound/full",
            method = RequestMethod.GET
    )
    public ResponseEntity getSoundMap() throws IOException {

        List<Integer> ranges = new ArrayList<>();
        ranges.add(4000);
        ranges.add(700000);

        Resource resource = resourceLoader.getResource("classpath:files/sounds/sound.mp3");

        File file = resource.getFile();

        int offset = ranges.get(0);
        int len = ranges.get(1) - offset;
        byte[] bytes = new byte[(int)file.length()];

        RandomAccessFile f = new RandomAccessFile(resource.getFile(),"r");

        int read = f.read(bytes, offset, len);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Type", "audio/mp3")
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", String.valueOf(read))
                .header("Accept-Ranges", "bytes " + ranges.get(0) + "-" + ranges.get(1) + "/" + file.length())
                .body(bytes);
    }

    @RequestMapping(
            value = "/map/sound/test",
            method = RequestMethod.GET
    )
    public ResponseEntity getSound() {
        RandomRead randomRead = new RandomRead();
        System.out.println(randomRead.getFileSize("sound.mp3"));
        return randomRead.prepareContent("sound", "mp3", "4000-700000");
    }

}
