package com.spot.spotserver.api.quiz.repository;

import com.spot.spotserver.api.quiz.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
