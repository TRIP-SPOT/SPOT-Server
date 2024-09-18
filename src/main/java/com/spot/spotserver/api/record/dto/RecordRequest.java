package com.spot.spotserver.api.record.dto;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RecordRequest {
    private String title;

    private String description;

    private Region region;

    private City city;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
