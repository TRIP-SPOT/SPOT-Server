package com.spot.spotserver.api.schedule.repository;

import com.spot.spotserver.api.schedule.domain.Location;
import com.spot.spotserver.api.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Integer countByScheduleAndDay(Schedule schedule, Integer day);
    List<Location> findAllBySchedule(Schedule schedule);
    List<Location> findAllByScheduleAndDayOrderBySeq(Schedule schedule, Integer day);
}
