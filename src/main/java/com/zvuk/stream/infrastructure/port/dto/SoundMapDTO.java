package com.zvuk.stream.infrastructure.port.dto;

import lombok.Data;


import java.util.List;
import java.util.Map;

@Data
public class SoundMapDTO {
    private int duration;
    private int minutes;
    private int seconds;
    private String minutesDuration;
    private String hoursDuration;
    private String format;
    private String formatLong;
    private Map<String, String> tags;
    private List<List<Long>> bytesList;
}
