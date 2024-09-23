package com.spot.spotserver.api.schedule.dto.request;

import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ScheduleRequest {
    private Region region;

    private City city;

    private LocalDate startDate;

    private LocalDate endDate;

    private MultipartFile image;

    public void setRegion(int region) {
        this.region = Region.values()[region];
    }

    public void setCity(int city) {
        this.city = City.values()[city];
    }
}
