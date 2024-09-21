package com.spot.spotserver.api.schedule.repository;

import com.spot.spotserver.api.schedule.domain.Location;
import com.spot.spotserver.api.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Integer countBySchedule(Schedule schedule);
    Integer countByScheduleAndDay(Schedule schedule, Integer day);
}
