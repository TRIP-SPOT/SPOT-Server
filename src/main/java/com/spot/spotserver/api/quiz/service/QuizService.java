package com.spot.spotserver.api.quiz.service;

import com.spot.spotserver.api.quiz.domain.Badge;
import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.quiz.dto.AnswerCheckResponse;
import com.spot.spotserver.api.quiz.dto.QuizResponse;
import com.spot.spotserver.api.quiz.repository.BadgeRepository;
import com.spot.spotserver.api.quiz.repository.QuizRepository;
import com.spot.spotserver.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {
    private final QuizRepository quizRepository;
    private final BadgeRepository badgeRepository;

    public QuizResponse getQuiz(Long id) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow();
        return new QuizResponse(quiz);
    }

    @Transactional
    public AnswerCheckResponse checkAnswer(Long id, Integer answer, User user) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow();
        boolean isCorrect = quiz.isCorrect(answer);

        if (isCorrect) {
            Badge badge = this.badgeRepository.findAllByUserAndRegion(user, quiz.getSpot().getRegion())
                    .orElseGet(() -> Badge.builder()
                            .region(quiz.getSpot().getRegion())
                            .user(user)
                            .build());
            badge.addBadge();
            this.badgeRepository.save(badge);
        }

        return new AnswerCheckResponse(isCorrect, quiz);
    }
}
