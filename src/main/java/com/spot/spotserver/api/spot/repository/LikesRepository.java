package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
