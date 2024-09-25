package com.spot.spotserver.api.user.service;

import com.spot.spotserver.api.auth.client.KakaoAccount;
import com.spot.spotserver.api.auth.dto.response.KakaoUserResponse;
import com.spot.spotserver.api.auth.dto.response.TokenResponse;
import com.spot.spotserver.api.auth.exception.JwtCustomException;
import com.spot.spotserver.api.auth.handler.UserAuthentication;
import com.spot.spotserver.api.auth.jwt.JwtTokenProvider;
import com.spot.spotserver.api.auth.jwt.JwtValidationType;
import com.spot.spotserver.api.auth.jwt.redis.RefreshTokenService;
import com.spot.spotserver.api.quiz.domain.Badge;
import com.spot.spotserver.api.quiz.dto.UserBadgeResponse;
import com.spot.spotserver.api.quiz.repository.BadgeRepository;
import com.spot.spotserver.api.spot.domain.Likes;
import com.spot.spotserver.api.spot.dto.response.UserLikedSpotsResponse;
import com.spot.spotserver.api.spot.repository.LikesRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.dto.request.ColorRequest;
import com.spot.spotserver.api.user.dto.request.NicknameRequest;
import com.spot.spotserver.api.user.dto.request.ProfileImageRequest;
import com.spot.spotserver.api.user.dto.response.ProfileResponse;
import com.spot.spotserver.api.user.exception.UserNotFoundException;
import com.spot.spotserver.api.user.repository.UserRepository;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.common.payload.ErrorCode;
import com.spot.spotserver.common.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;
    private final BadgeRepository badgeRepository;
    private final RefreshTokenService refreshTokenService;
    private final S3Service s3Service;

    public Long createUser(final KakaoUserResponse userResponse) {
        String email = Optional.ofNullable(userResponse.kakaoAccount())
                .map(KakaoAccount::email)
                .orElse(null);
        User user = User.of(email, userResponse.id());
        return userRepository.save(user).getId();
    }

    public Long getIdBySocialId(final Long socialId) {
        User user = userRepository.findBySocialId(socialId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        return user.getId();
    }

    public boolean isExistingUser(final Long socialId) {
        return userRepository.findBySocialId(socialId).isPresent();
    }

    public TokenResponse getTokenByUserId(final Long id) {
        UserAuthentication userAuthentication = new UserAuthentication(id, null, null);
        String refreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);
        refreshTokenService.saveRefreshToken(id, refreshToken);
        return TokenResponse.of(
                jwtTokenProvider.issueAccessToken(userAuthentication),
                refreshToken
        );
    }

    public TokenResponse reissueToken(final String refreshToken) {
        JwtValidationType validationType = jwtTokenProvider.validateToken(refreshToken);
        if (validationType != JwtValidationType.VALID_JWT) {
            throw new JwtCustomException(ErrorCode.INVALID_JWT_TOKEN);
        }

        Long userId = jwtTokenProvider.getUserFromJwt(refreshToken);
        UserAuthentication userAuthentication = new UserAuthentication(userId, null, null);
        String newAccessToken = jwtTokenProvider.issueAccessToken(userAuthentication);
        String newRefreshToken = jwtTokenProvider.issueRefreshToken(userAuthentication);

        // 새로운 리프레시 토큰으로 교체
        refreshTokenService.saveRefreshToken(userId, newRefreshToken);
        return TokenResponse.of(newAccessToken, newRefreshToken);
    }

    public String saveNickname(NicknameRequest nicknameRequest, User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        user.setNickname(nicknameRequest.nickname());
        userRepository.save(user);
        return nicknameRequest.nickname();
    }

    public String getNickname(User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        return user.getNickname();
    }

    public String saveProfile(ProfileImageRequest profileImageRequest, User user) throws IOException {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        String profileUrl = s3Service.upload(profileImageRequest.profileImage(), user.getNickname());
        user.setProfileUrl(profileUrl);
        user.setColor(null);
        userRepository.save(user);
        return profileUrl;
    }

    public String saveColor(ColorRequest colorRequest, User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        user.setColor(colorRequest.colorCode());
        user.setProfileUrl(null);
        userRepository.save(user);
        return colorRequest.colorCode();
    }

    public ProfileResponse getProfile(User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        if (Objects.isNull(user.getProfileUrl())) {
            return new ProfileResponse(null, user.getColor(), user.getNickname());
        }
        else {
            return new ProfileResponse(user.getProfileUrl(), null, null);
        }
    }

    public List<UserLikedSpotsResponse> getLikedSpots(User user) {
        List<Likes> likedSpots = likesRepository.findByUser(user);

        if (likedSpots.isEmpty()) {
            return Collections.emptyList();
        }

        return likedSpots.stream()
                .map(likes -> UserLikedSpotsResponse.fromEntity(likes.getSpot()))
                .collect(Collectors.toList());
    }

    public List<UserBadgeResponse> getBadgeCountByRegion(User user) {
        List<Badge> badges = badgeRepository.findByUser(user);
        List<UserBadgeResponse> userBadges = new ArrayList<>();

        for (Region region : Region.values()) {
            userBadges.add(new UserBadgeResponse(region.ordinal(), 0));
        }

        for (Badge badge : badges) {
            int regionOrdinal = badge.getRegion().ordinal();
            userBadges.set(regionOrdinal, new UserBadgeResponse(regionOrdinal, badge.getCount()));
        }
        return  userBadges;
    }
}
