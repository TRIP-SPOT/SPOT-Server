package com.spot.spotserver.api.schedule.dto;

import com.spot.spotserver.api.schedule.domain.Schedule;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleResponse {
    private Region region;

    private City city;

    private LocalDate startDate;

    private LocalDate endDate;

    public ScheduleResponse(Schedule schedule) {
        this.region = schedule.getRegion();
        this.city = schedule.getCity();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
    }
}
