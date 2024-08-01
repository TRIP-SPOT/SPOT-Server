package com.spot.spotserver.api.schedule.repository;

import com.spot.spotserver.api.schedule.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
