package com.zvuk.stream.infrastructure.services;

import com.zvuk.stream.domain.entities.Track;
import com.zvuk.stream.domain.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {
    @Autowired
    private TrackRepository trackRepository;

    public Track getTrackByIdAndUserId(int trackId, int userId) {
        return trackRepository.findTrackByIdAndUserId(trackId, userId);
    }
}
