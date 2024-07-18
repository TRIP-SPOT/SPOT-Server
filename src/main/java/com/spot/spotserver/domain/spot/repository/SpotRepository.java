package com.spot.spotserver.domain.spot.repository;

import com.spot.spotserver.domain.spot.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}
