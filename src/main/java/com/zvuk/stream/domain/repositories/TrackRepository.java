package com.zvuk.stream.domain.repositories;

import com.zvuk.stream.domain.entities.Track;
import com.zvuk.stream.infrastructure.services.TrackService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("TrackRepository")
public interface TrackRepository extends JpaRepository<Track, Long> {

    Track findTrackByIdAndUserId(int id, int userId);

    @Query(value = "SELECT * FROM tracks", nativeQuery = true)
    List<Track> all();

    List<Track> findAllByUserId(int userId);
}
