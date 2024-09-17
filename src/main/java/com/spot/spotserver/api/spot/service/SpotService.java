package com.spot.spotserver.api.spot.service;

import com.spot.spotserver.api.spot.client.CommonInfoClient;
import com.spot.spotserver.api.spot.client.LocationBasedListClient;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.spot.dto.response.CommonInfoResponse;
import com.spot.spotserver.api.spot.dto.response.LocationBasedResponse;
import com.spot.spotserver.api.spot.dto.response.SpotAroundResponse;
import com.spot.spotserver.api.spot.dto.response.SpotDetailsResponse;
import com.spot.spotserver.api.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final CommonInfoClient commonInfoClient;
    private final LocationBasedListClient locationBasedListClient;
    private final SpotRepository spotRepository;

    @Value("${tourApi.serviceKey}")
    private String serviceKey;
    private static final String mobileOS = "AND";
    private static final String mobileApp = "SPOT";
    private static final String _type = "json";

    public SpotDetailsResponse getSpotDetails(Integer contentId) {

        CommonInfoResponse commonInfo = commonInfoClient.getCommonInfo(
                mobileOS, mobileApp, _type, contentId.toString(), "Y", "Y", "Y", "Y", "Y", serviceKey
        );

        if (commonInfo == null || commonInfo.response().body().items().item().isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }

        SpotDetailsResponse spotDetailsResponse = SpotDetailsResponse.fromCommonInfo(commonInfo.response().body().items().item().get(0));
        return spotDetailsResponse;
    }

    public SpotAroundResponse getSpotAroundList(Integer contentId) {

        Optional<Spot> spot = Optional.ofNullable(spotRepository.findByContentId(contentId).orElseThrow(() -> new IllegalArgumentException("해당하는 촬영지가 존재하지 않습니다.")));
        Double longitude = spot.get().getLongitude();
        Double latitude = spot.get().getLatitude();

        LocationBasedResponse attractionResponse;
        LocationBasedResponse restaurantResponse;
        LocationBasedResponse accommodationResponse;

        try {
            attractionResponse = locationBasedListClient.getLocationBasedList(
                    mobileOS, mobileApp, _type, "S", longitude.toString(), latitude.toString(), "5000", "12", serviceKey
            );
            restaurantResponse = locationBasedListClient.getLocationBasedList(
                    mobileOS, mobileApp, _type, "S", longitude.toString(), latitude.toString(), "5000", "39", serviceKey
            );
            accommodationResponse = locationBasedListClient.getLocationBasedList(
                    mobileOS, mobileApp, _type, "S", longitude.toString(), latitude.toString(), "5000", "32", serviceKey
            );
        } catch (Exception e) {
            return new SpotAroundResponse(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }

        return SpotAroundResponse.fromLocationBasedList(attractionResponse, restaurantResponse, accommodationResponse);
    }
}
