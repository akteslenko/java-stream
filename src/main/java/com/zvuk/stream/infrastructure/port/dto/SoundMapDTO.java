package com.zvuk.stream.infrastructure.port.dto;

import lombok.Data;


import java.util.List;

@Data
public class SoundMapDTO {
    private int duration;
    private int minutes;
    private int seconds;
    private List<List<Long>> bytesList;
}
