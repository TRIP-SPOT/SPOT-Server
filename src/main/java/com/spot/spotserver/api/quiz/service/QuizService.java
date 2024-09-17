package com.spot.spotserver.api.quiz.service;

import com.spot.spotserver.api.quiz.domain.Badge;
import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.quiz.dto.AnswerCheckRequest;
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

    public AnswerCheckResponse checkAnswer(AnswerCheckRequest answerCheckRequest, User user) {
        Quiz quiz = this.quizRepository.findById(answerCheckRequest.getId()).orElseThrow();
        boolean isCorrect = quiz.isCorrect(answerCheckRequest.getAnswer());

        if (isCorrect) {
            Badge badge = this.badgeRepository.findAllByUserAndRegion(user, quiz.getRegion()).orElseThrow();
            badge.addBadge();
        }

        return new AnswerCheckResponse(isCorrect);
    }
}
