package com.spot.spotserver.api.schedule.dto;

import com.spot.spotserver.api.schedule.domain.Schedule;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DetailedScheduleResponse {
    private Long id;

    private Integer region;

    private Integer city;

    private LocalDate startDate;

    private LocalDate endDate;

    private String image;

    private List<LocationResponse> locations;

    public DetailedScheduleResponse(Schedule schedule, List<LocationResponse> locations) {
        this.id = schedule.getId();
        this.region = schedule.getRegion().ordinal();
        this.city = schedule.getCity().ordinal();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        this.image = schedule.getImage();
        this.locations = locations;
    }
}
