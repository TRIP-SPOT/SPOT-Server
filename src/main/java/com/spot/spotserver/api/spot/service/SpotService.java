package com.spot.spotserver.api.spot.service;

import com.spot.spotserver.api.spot.client.CommonInfoClient;
import com.spot.spotserver.api.spot.dto.response.CommonInfoResponse;
import com.spot.spotserver.api.spot.dto.response.SpotDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.spot.dto.response.AccessibleSpotResponse;
import com.spot.spotserver.api.spot.repository.SpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotService {
    private final CommonInfoClient commonInfoClient;
    private final SpotRepository spotRepository;
    private static final double EARTH_RADIUS = 6371.0;
    private static final double ACCESS_RADIUS = 20.0;

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

    public List<AccessibleSpotResponse> getAccessibleSpot(double userLatitude, double userLongitude) {
        return this.spotRepository.findAll()
                .stream()
                .filter((spot) -> this.isWithinAccessRadius(userLatitude, userLongitude, spot))
                .map(AccessibleSpotResponse::new)
                .toList();
    }

    private boolean isWithinAccessRadius(double userLatitude, double userLongitude, Spot spot) {
        double distance = this.calculateDistance(userLatitude, userLongitude, spot.getLatitude(), spot.getLongitude());
        return distance <= ACCESS_RADIUS;
    }

    private double calculateDistance(double userLatitude, double userLongitude, double quizLatitude, double quizLongitude) {
        double userLatitudeRadian = Math.toRadians(userLatitude);
        double userLongitudeRadian = Math.toRadians(userLongitude);
        double quizLatitudeRadian = Math.toRadians(quizLatitude);
        double quizLongitudeRadian = Math.toRadians(quizLongitude);

        double deltaLatitude = quizLatitudeRadian - userLatitudeRadian;
        double deltaLongitude = quizLongitudeRadian - userLongitudeRadian;

        double haversineValue = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2)
                + Math.cos(userLatitudeRadian) * Math.cos(quizLatitudeRadian)
                * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

        double angularDistance = 2 * Math.atan2(Math.sqrt(haversineValue), Math.sqrt(1 - haversineValue));

        return EARTH_RADIUS * angularDistance;
    }
}
