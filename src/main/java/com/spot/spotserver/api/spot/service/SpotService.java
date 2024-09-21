package com.spot.spotserver.api.spot.service;

import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.quiz.repository.QuizRepository;
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
import com.spot.spotserver.api.spot.dto.response.AccessibleSpotResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotService {
  
    private final CommonInfoClient commonInfoClient;
    private final LocationBasedListClient locationBasedListClient;
    private final SpotRepository spotRepository;
    private final QuizRepository quizRepository;
  
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

        SpotDetailsResponse spotDetailsResponse = SpotDetailsResponse.fromCommonInfo(commonInfo.response().body().items().item().get(0), spotRepository);
        return spotDetailsResponse;
    }

    public List<AccessibleSpotResponse> getAccessibleSpot(double userLatitude, double userLongitude) {
        return this.spotRepository.findAll()
                .stream()
                .filter((spot) -> this.isWithinAccessRadius(userLatitude, userLongitude, spot))
                .map((spot) -> {
                    Quiz quiz = this.quizRepository.findBySpot(spot).orElseThrow();
                    return new AccessibleSpotResponse(spot, quiz.getId());
                })
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
