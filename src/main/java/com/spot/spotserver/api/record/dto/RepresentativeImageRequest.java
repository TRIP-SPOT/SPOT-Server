package com.spot.spotserver.api.record.dto;

import com.spot.spotserver.api.record.domain.Region;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class RepresentativeImageRequest {
    private Region region;

    private MultipartFile image;

    public void setRegion(int region) {
        this.region = Region.values()[region];
    }
}
