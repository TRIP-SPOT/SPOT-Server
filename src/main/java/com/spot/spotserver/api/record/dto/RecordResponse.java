package com.spot.spotserver.api.record.dto;

import com.spot.spotserver.api.record.domain.City;
import com.spot.spotserver.api.record.domain.Record;
import com.spot.spotserver.api.record.domain.Region;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecordResponse {
    private Long id;

    private String title;

    private String description;

    private Region region;

    private City city;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<String> images;

    public RecordResponse(Record record, List<String> images) {
        this.id = record.getId();
        this.title = record.getTitle();
        this.description = record.getDescription();
        this.region = record.getRegion();
        this.city = record.getCity();
        this.startDate = record.getStartDate();
        this.endDate = record.getEndDate();
        this.images = images;
    }
}
