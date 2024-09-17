package com.spot.spotserver.api.quiz.controller;

import com.spot.spotserver.api.quiz.dto.AnswerCheckRequest;
import com.spot.spotserver.api.quiz.dto.AnswerCheckResponse;
import com.spot.spotserver.api.quiz.dto.QuizResponse;
import com.spot.spotserver.api.quiz.service.QuizService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/")
    public QuizResponse getQuiz(Long id) {
        return this.quizService.getQuiz(id);
    }

    @PostMapping("/answer")
    public AnswerCheckResponse checkAnswer(@RequestBody @Valid AnswerCheckRequest answerCheckRequest,
                                           @CurrentUser User user) {
        return this.quizService.checkAnswer(answerCheckRequest, user);
    }
}
