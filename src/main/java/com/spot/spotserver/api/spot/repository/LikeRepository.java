package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
