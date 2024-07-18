package com.spot.spotserver.domain.schedule.repository;

import com.spot.spotserver.domain.schedule.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
