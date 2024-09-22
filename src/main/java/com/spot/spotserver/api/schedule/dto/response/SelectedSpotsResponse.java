package com.spot.spotserver.api.schedule.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SelectedSpotsResponse {
    private List<SelectedSpotResponse> attraction;

    private List<SelectedSpotResponse> restaurant;

    private List<SelectedSpotResponse> accommodation;

    public SelectedSpotsResponse(List<SelectedSpotResponse> attraction, List<SelectedSpotResponse> restaurant, List<SelectedSpotResponse> accommodation) {
        this.attraction = attraction;
        this.restaurant = restaurant;
        this.accommodation = accommodation;
    }
}
