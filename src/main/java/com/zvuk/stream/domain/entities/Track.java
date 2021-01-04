package com.zvuk.stream.domain.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name="track")
public class Track {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    private Integer duration;
    private String formattedDuration;
    private Integer stepSeconds;
    private String path;
    private String format;
    private Timestamp createdAt;
    private Timestamp updateAt;
    private Timestamp deletedAt;
}
