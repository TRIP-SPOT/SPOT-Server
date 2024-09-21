package com.spot.spotserver.api.schedule.repository;

import com.spot.spotserver.api.schedule.domain.Schedule;
import com.spot.spotserver.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUser(User user);
}
