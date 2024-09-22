package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Likes;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUserAndSpot(User user, Spot spot);

    Integer countBySpot(Spot spot);
}
