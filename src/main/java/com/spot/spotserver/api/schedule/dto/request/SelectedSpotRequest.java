package com.spot.spotserver.api.schedule.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SelectedSpotRequest {
    private String title;

    private String addr1;

    private String addr2;

    private String contentId;

    private String contentTypeId;

    private String dist;

    private String image;
}
