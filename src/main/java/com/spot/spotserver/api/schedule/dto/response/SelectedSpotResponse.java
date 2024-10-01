package com.spot.spotserver.api.schedule.dto.response;

import com.spot.spotserver.api.schedule.domain.SelectedSpot;
import lombok.Data;

@Data
public class SelectedSpotResponse {
    private Long id;

    private String title;

    private String addr1;

    private String addr2;

    private String contentId;

    private String contentTypeId;

    private String dist;

    private String image;

    public SelectedSpotResponse(SelectedSpot spot) {
        this.id = spot.getId();
        this.title = spot.getTitle();
        this.addr1 = spot.getAddr1();
        this.addr2 = spot.getAddr2();
        this.contentId = spot.getContentId();
        this.contentTypeId = spot.getContentTypeId();
        this.dist = spot.getDist();
        this.image = spot.getImage();
    }
}
