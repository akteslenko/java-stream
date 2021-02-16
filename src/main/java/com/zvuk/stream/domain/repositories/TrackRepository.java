package com.zvuk.stream.domain.repositories;

import com.zvuk.stream.domain.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("TrackRepository")
public interface TrackRepository extends JpaRepository<Track, Integer> {

}
