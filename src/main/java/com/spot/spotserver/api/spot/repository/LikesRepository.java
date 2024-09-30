package com.spot.spotserver.api.spot.repository;

import com.spot.spotserver.api.spot.domain.Likes;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.spot.dto.response.TopLikedSpotResponse;
import com.spot.spotserver.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByUser(User user);
    Optional<Likes> findByUserAndSpot(User user, Spot spot);
    Integer countBySpot(Spot spot);
    @Query("SELECT l.spot, COUNT(l) as likeCount FROM Likes l GROUP BY l.spot ORDER BY likeCount DESC")
    List<Object[]> findTop5SpotsByLikes();
}
