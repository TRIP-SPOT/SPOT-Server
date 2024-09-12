package com.spot.spotserver.api.record.dto;

import com.spot.spotserver.api.record.domain.City;
import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.domain.Region;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegionalRecordResponse {
    private Long id;

    private String title;

    private Region region;

    private City city;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String image;

    public RegionalRecordResponse(Record record, String image) {
        this.id = record.getId();
        this.title = record.getTitle();
        this.region = record.getRegion();
        this.city = record.getCity();
        this.startDate = record.getStartDate();
        this.endDate = record.getEndDate();
        this.image = image;
    }
}
