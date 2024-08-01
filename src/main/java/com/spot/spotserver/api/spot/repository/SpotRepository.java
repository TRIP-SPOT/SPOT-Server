package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long> {
}
