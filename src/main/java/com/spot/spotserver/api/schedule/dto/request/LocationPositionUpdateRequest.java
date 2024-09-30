package com.spot.spotserver.api.schedule.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationPositionUpdateRequest {
    private Integer day;

    private Double before;

    private Double after;

    public Double getMiddle() {
        return (this.before + this.after) / 2;
    }
}
