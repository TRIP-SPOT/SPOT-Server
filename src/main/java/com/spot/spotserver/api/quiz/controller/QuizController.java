package com.spot.spotserver.api.quiz.controller;

import com.spot.spotserver.api.quiz.dto.QuizResponse;
import com.spot.spotserver.api.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping()
    public QuizResponse getQuiz(Long id) {
        return this.quizService.getQuiz(id);
    }
}
