package com.spot.spotserver.api.schedule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ScheduleDurationUpdateRequest {
    private LocalDate startDate;

    private LocalDate endDate;
}
