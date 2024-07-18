package com.spot.spotserver.domain.schedule.repository;

import com.spot.spotserver.domain.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
