package com.spot.spotserver.api.badge.repository;

import com.spot.spotserver.api.badge.domain.Badge;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByUser(User user);
    Integer countByUser(User user);
    Integer countByUserAndRegion(User user, Region region);
    Integer countByUserAndCity(User user, City city);
    List<Badge> findByUserAndRegionIn(User user, List<Region> regions);
}
