package com.spot.spotserver.api.quiz.repository;

import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.common.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findBySpot(Spot spot);
    List<Quiz> findByCity(City city);
}
