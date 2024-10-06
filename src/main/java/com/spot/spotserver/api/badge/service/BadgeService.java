package com.spot.spotserver.api.badge.service;

import com.spot.spotserver.api.badge.domain.AcquisitionType;
import com.spot.spotserver.api.badge.domain.Badge;
import com.spot.spotserver.api.badge.dto.response.BadgeAcquisitionResponse;
import com.spot.spotserver.api.badge.repository.BadgeRepository;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.api.user.repository.UserRepository;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.MajorCity;
import com.spot.spotserver.common.domain.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final UserRepository userRepository;

    public void createBadge(AcquisitionType type, Region region, City city, User user) {
        if (!this.canCreateBadge(user, city)) { return; }

        Badge badge = Badge.builder()
                .region(region)
                .city(city)
                .user(user)
                .acquisitionType(type)
                .build();

        this.badgeRepository.save(badge);

        user.updateProfileLevel(this.badgeRepository.countByUser(user));
        this.userRepository.save(user);
    }

    private boolean canCreateBadge(User user, City city) {
        boolean isMajor = this.isMajorCity(city);
        int badgeCount = this.badgeRepository.countByUserAndCity(user, city);

        return isMajor ? badgeCount < 5 : badgeCount < 1;
    }

    private boolean isMajorCity(City city) {
        return EnumSet.allOf(MajorCity.class).stream()
                .anyMatch(majorCity -> majorCity.name().equals(city.name()));
    }

    public List<BadgeAcquisitionResponse> getBadgeAcquisition(User user, List<Region> regions) {
        List<Badge> badges = badgeRepository.findByUserAndRegionIn(user, regions);
        return badges.stream()
                .map(BadgeAcquisitionResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
