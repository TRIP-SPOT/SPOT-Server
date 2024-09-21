package com.spot.spotserver.api.schedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationRequest {
    private Long scheduleId;

    private String name;

    private String description;
}
