package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    Optional<Spot> findByContentId(Integer contentId);
    Optional<Spot> findByContentIdAndWorkId(Integer contentId, Long workId);
    List<Spot> findByWorkId(Long workId);
}
