package com.spot.spotserver.api.schedule.dto.response;

import com.spot.spotserver.api.schedule.domain.Schedule;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
public class ScheduleDurationUpdateResponse {

    private LocalDate startDate;

    private LocalDate endDate;

    public ScheduleDurationUpdateResponse(Schedule schedule) {
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
    }
}
