package com.zvuk.stream.domain.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.TABLE;

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
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;
    private Timestamp deletedAt;
}
