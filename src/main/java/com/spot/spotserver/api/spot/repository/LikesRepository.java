package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Likes;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByUserAndSpot(User user, Spot spot);
}
