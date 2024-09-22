package com.spot.spotserver.api.schedule.dto.response;

import com.spot.spotserver.api.schedule.domain.Location;
import lombok.Data;

@Data
public class LocationResponse {
    private Long id;

    private Integer day;

    private Integer seq;

    private String name;

    private String description;

    public LocationResponse(Location location) {
        this.id = location.getId();
        this.day = location.getDay();
        this.seq = location.getSeq();
        this.name = location.getName();
        this.description = location.getDescription();
    }
}
