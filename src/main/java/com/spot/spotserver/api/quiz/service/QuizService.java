package com.spot.spotserver.api.quiz.service;

import com.spot.spotserver.api.badge.domain.AcquisitionType;
import com.spot.spotserver.api.badge.service.BadgeService;
import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.quiz.dto.AnswerCheckResponse;
import com.spot.spotserver.api.quiz.dto.QuizResponse;
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
    private final BadgeService badgeService;

    public QuizResponse getQuiz(Long id) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow();
        return new QuizResponse(quiz);
    }

    @Transactional
    public AnswerCheckResponse checkAnswer(Long id, Integer answer, User user) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow();
        boolean isCorrect = quiz.isCorrect(answer);

        if (isCorrect) {
            this.badgeService.createBadge(AcquisitionType.BY_QUIZ, quiz.getSpot().getRegion(), quiz.getSpot().getCity(), user);
        }

        return new AnswerCheckResponse(isCorrect, quiz);
    }
}
