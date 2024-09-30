package com.spot.spotserver.api.spot.service;

import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.quiz.repository.QuizRepository;
import com.spot.spotserver.api.spot.client.CommonInfoClient;
import com.spot.spotserver.api.spot.client.LocationBasedListClient;
import com.spot.spotserver.api.spot.domain.Likes;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.spot.domain.Work;
import com.spot.spotserver.api.spot.dto.response.*;
import com.spot.spotserver.api.spot.exception.LikeAlreadyExistException;
import com.spot.spotserver.api.spot.exception.LikeNotFoundException;
import com.spot.spotserver.api.spot.exception.SpotNotFoundException;
import com.spot.spotserver.api.spot.exception.WorkNotFoundException;
import com.spot.spotserver.api.spot.repository.LikesRepository;
import com.spot.spotserver.api.spot.repository.SpotRepository;
import com.spot.spotserver.api.spot.repository.WorkRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.exception.UserNotFoundException;
import com.spot.spotserver.api.user.repository.UserRepository;
import com.spot.spotserver.common.domain.SpotType;
import com.spot.spotserver.common.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpotService {

    private final CommonInfoClient commonInfoClient;
    private final LocationBasedListClient locationBasedListClient;
    private final SpotRepository spotRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final WorkRepository workRepository;

    private static final double EARTH_RADIUS = 6371.0;
    private static final double ACCESS_RADIUS = 20.0;
    private static final long QUIZ_COUNT = 5;

    @Value("${tourApi.serviceKey}")
    private String serviceKey;
    private static final String mobileOS = "AND";
    private static final String mobileApp = "SPOT";
    private static final String _type = "json";

    public SpotDetailsResponse getSpotDetails(Integer contentId, Long workId, User user) {

        CommonInfoResponse commonInfo = commonInfoClient.getCommonInfo(
                mobileOS, mobileApp, _type, contentId.toString(), "Y", "Y", "Y", "Y", "Y", serviceKey
        );

        if (commonInfo == null || commonInfo.response().body().items().item().isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }

        Spot spot = spotRepository.findByContentIdAndWorkId(contentId, workId)
                .orElseThrow(() -> new SpotNotFoundException(ErrorCode.SPOT_NOT_FOUND));

        SpotDetailsResponse spotDetailsResponse = SpotDetailsResponse.fromCommonInfo(
                commonInfo.response().body().items().item().get(0), spot, likesRepository, user);

        return spotDetailsResponse;
    }

    public List<AccessibleSpotResponse> getAccessibleSpot(double userLatitude, double userLongitude) {
        return this.spotRepository.findAll()
                .stream()
                .filter((spot) -> this.isWithinAccessRadius(userLatitude, userLongitude, spot))
                .filter(this.quizRepository::existsBySpot)
                .sorted(Comparator.comparing((spot) -> this.calculateDistance(userLatitude, userLongitude, spot.getLatitude(), spot.getLongitude())))
                .limit(QUIZ_COUNT)
                .map((spot) -> {
                    Quiz quiz = this.quizRepository.findBySpot(spot)
                            .or(() -> this.quizRepository.findByCity(spot.getCity()))
                            .orElseThrow();
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

    public SpotAroundResponse getSpotAroundList(Integer contentId, Long workId) {

        Spot spot = spotRepository.findByContentIdAndWorkId(contentId, workId)
                .orElseThrow(() -> new SpotNotFoundException(ErrorCode.SPOT_NOT_FOUND));
        Double longitude = spot.getLongitude();
        Double latitude = spot.getLatitude();

        LocationBasedResponse attractionResponse;
        LocationBasedResponse restaurantResponse;
        LocationBasedResponse accommodationResponse;

        try {
            attractionResponse = locationBasedListClient.getLocationBasedList(
                    mobileOS, mobileApp, _type, "S", longitude.toString(), latitude.toString(), "10000", SpotType.ATTRACTION.type(), serviceKey
            );
            restaurantResponse = locationBasedListClient.getLocationBasedList(
                    mobileOS, mobileApp, _type, "S", longitude.toString(), latitude.toString(), "10000", SpotType.RESTAURANT.type(), serviceKey
            );
            accommodationResponse = locationBasedListClient.getLocationBasedList(
                    mobileOS, mobileApp, _type, "S", longitude.toString(), latitude.toString(), "10000", SpotType.ACCOMMODATION.type(), serviceKey
            );
        } catch (Exception e) {
            return new SpotAroundResponse(Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
        }

        return SpotAroundResponse.fromLocationBasedList(attractionResponse, restaurantResponse, accommodationResponse);
    }

    public AroundDetailsResponse getAroundDetails(Integer contentId) {

        CommonInfoResponse commonInfo = commonInfoClient.getCommonInfo(
                mobileOS, mobileApp, _type, contentId.toString(), "Y", "Y", "Y", "Y", "Y", serviceKey
        );

        if (commonInfo == null || commonInfo.response().body().items().item().isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }

        AroundDetailsResponse aroundDetailsResponse = AroundDetailsResponse.fromCommonInfo(commonInfo.response().body().items().item().get(0));
        return aroundDetailsResponse;
    }

    @Transactional
    public void likeSpot(Long spotId, User user) {

        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException(ErrorCode.SPOT_NOT_FOUND));

        Optional<Likes> existingLike = likesRepository.findByUserAndSpot(user, spot);
        if (existingLike.isPresent()) {
            throw new LikeAlreadyExistException(ErrorCode.LIKE_ALREADY_EXIST);
        }

        Likes likes = Likes.builder()
                .user(user)
                .spot(spot)
                .build();
        likesRepository.save(likes);
    }

    @Transactional
    public void unlikeSpot(Long spotId, User user) {

        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new SpotNotFoundException(ErrorCode.SPOT_NOT_FOUND));

        Optional<Likes> likes = likesRepository.findByUserAndSpot(user, spot);
        if (likes.isPresent()) {
            likesRepository.delete(likes.get());
        } else {
            throw new LikeNotFoundException(ErrorCode.LIKE_NOT_FOUND);
        }
    }

    public List<TopLikedSpotResponse> getTop5Spots(User user) {
        List<Object[]> topSpots = likesRepository.findTop5SpotsByLikes();
        List<TopLikedSpotResponse> responses = new ArrayList<>();

        for (Object[] topSpot : topSpots) {
            Spot spot = (Spot) topSpot[0];
            Long likeCount = (Long) topSpot[1];

            boolean isLiked = likesRepository.findByUserAndSpot(user, spot).isPresent();

            // TopLikedSpotResponse로 변환하여 리스트에 추가
            TopLikedSpotResponse response = TopLikedSpotResponse.fromEntity(spot, isLiked, likeCount.intValue());
            responses.add(response);
        }

        if (responses.size() < 5) {
            List<Spot> allSpots = spotRepository.findAll();
            Collections.shuffle(allSpots);

            for (Spot spot : allSpots) {
                if (responses.size() >= 5) break;

                // 이미 포함된 Spot 제외
                if (responses.stream().noneMatch(r -> r.id().equals(spot.getId()))) {
                    boolean isLiked = likesRepository.findByUserAndSpot(user, spot).isPresent();
                    int likeCount = likesRepository.countBySpot(spot);
                    TopLikedSpotResponse response = TopLikedSpotResponse.fromEntity(spot, isLiked, likeCount); // 좋아요 수는 0으로 설정
                    responses.add(response);
                }
            }
        }
        return responses;
    }

    public List<SpotSearchResponse> searchSpotsByWorkName(String workName, User user) {
        List<Work> works = workRepository.findByNameContainingIgnoreCase(workName);
        if (works.isEmpty()) {
            return new ArrayList<>();
        }

        List<SpotSearchResponse> responses = new ArrayList<>();

        for (Work work : works) {
            List<Spot> spots = spotRepository.findByWorkId(work.getId());
            if (spots.isEmpty()) {
                continue;
            }

            for (Spot spot : spots) {
                Boolean isLiked = likesRepository.findByUserAndSpot(user, spot).isPresent();
                Integer likeCount = likesRepository.countBySpot(spot);
                responses.add(SpotSearchResponse.fromEntity(spot, isLiked, likeCount));
            }
        }
        return responses;
    }
}
