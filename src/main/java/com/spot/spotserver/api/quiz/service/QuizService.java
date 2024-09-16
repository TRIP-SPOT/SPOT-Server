package com.spot.spotserver.api.quiz.service;

import com.spot.spotserver.api.quiz.domain.Quiz;
import com.spot.spotserver.api.quiz.dto.QuizResponse;
import com.spot.spotserver.api.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizResponse getQuiz(Long id) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow();
        return new QuizResponse(quiz);
    }
}
