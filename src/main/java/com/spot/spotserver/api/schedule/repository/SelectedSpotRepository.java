package com.spot.spotserver.api.schedule.repository;

import com.spot.spotserver.api.schedule.domain.Schedule;
import com.spot.spotserver.api.schedule.domain.SelectedSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SelectedSpotRepository extends JpaRepository<SelectedSpot, Long> {
    List<SelectedSpot> findAllBySchedule(Schedule schedule);
}
