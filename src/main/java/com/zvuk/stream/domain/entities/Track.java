package com.zvuk.stream.domain.entities;

import lombok.Data;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name="tracks")
public class Track {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String name;
    private Integer duration;
    private String formattedDuration;
    private String format;
    private String path;
    private Integer stepSeconds;
    private Integer userId;
    private Timestamp createdAt;
    private Timestamp updateAt;
    private Timestamp deletedAt;
}
