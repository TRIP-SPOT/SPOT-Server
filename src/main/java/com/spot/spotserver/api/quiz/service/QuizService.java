package com.spot.spotserver.api.quiz.service;

import com.spot.spotserver.api.badge.domain.AcquisitionType;
import com.spot.spotserver.api.badge.service.BadgeService;
import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.quiz.dto.AnswerCheckResponse;
import com.spot.spotserver.api.quiz.dto.QuizResponse;
import com.spot.spotserver.api.quiz.repository.QuizRepository;
import com.spot.spotserver.api.spot.domain.Spot;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.domain.City;
import com.spot.spotserver.common.domain.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {
    private final QuizRepository quizRepository;
    private final BadgeService badgeService;

    public QuizResponse getQuiz(Long id) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow();
        return new QuizResponse(quiz);
    }

    @Transactional
    public AnswerCheckResponse checkAnswer(Long id, Integer answer, User user) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow();
        boolean isCorrect = quiz.isCorrect(answer);

        Region region = Optional.ofNullable(quiz.getSpot())
                .map(Spot::getRegion)
                .orElse(quiz.getRegion());

        City city = Optional.ofNullable(quiz.getSpot())
                .map(Spot::getCity)
                .orElse(quiz.getCity());

        if (isCorrect) {
            this.badgeService.createBadge(AcquisitionType.BY_QUIZ, region, city, user);
        }

        return new AnswerCheckResponse(isCorrect, region, city);
    }
}
