package com.spot.spotserver.domain.quiz.repository;

import com.spot.spotserver.domain.quiz.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
