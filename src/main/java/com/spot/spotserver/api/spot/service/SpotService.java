package com.spot.spotserver.api.spot.service;

import com.spot.spotserver.api.spot.client.CommonInfoClient;
import com.spot.spotserver.api.spot.dto.response.CommonInfoResponse;
import com.spot.spotserver.api.spot.dto.response.SpotDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotService {

    private final CommonInfoClient commonInfoClient;

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
}
