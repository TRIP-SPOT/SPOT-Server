package com.spot.spotserver.api.schedule.repository;

import com.spot.spotserver.api.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
