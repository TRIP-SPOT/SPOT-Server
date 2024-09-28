package com.spot.spotserver.api.quiz.repository;

import com.spot.spotserver.api.quiz.domain.Badge;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import com.spot.spotserver.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    List<Badge> findByUser(User user);
    Integer countByUserAndRegion(User user, Region region);
    boolean existsByUserAndCity(User user, City city);
}
