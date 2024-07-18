package com.spot.spotserver.domain.spot.repository;

import com.spot.spotserver.domain.spot.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
