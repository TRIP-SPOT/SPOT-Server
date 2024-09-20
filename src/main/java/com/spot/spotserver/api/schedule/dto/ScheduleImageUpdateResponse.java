package com.spot.spotserver.api.schedule.dto;

import lombok.Data;

@Data
public class ScheduleImageUpdateResponse {
    private String image;

    public ScheduleImageUpdateResponse(String image) {
        this.image = image;
    }
}
