package com.spot.spotserver.api.quiz.controller;

import com.spot.spotserver.api.quiz.dto.AnswerCheckResponse;
import com.spot.spotserver.api.quiz.dto.QuizResponse;
import com.spot.spotserver.api.quiz.service.QuizService;
import com.spot.spotserver.api.user.domain.User;
import com.spot.spotserver.common.annotation.CurrentUser;
import com.spot.spotserver.common.payload.ApiResponse;
import com.spot.spotserver.common.payload.SuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{id}")
    public ApiResponse<QuizResponse> getQuiz(@PathVariable Long id) {
        return ApiResponse.success(SuccessCode.GET_QUIZ_SUCCESS, this.quizService.getQuiz(id));
    }

    @PostMapping("/answer/{id}")
    public ApiResponse<AnswerCheckResponse> checkAnswer(@PathVariable Long id,
                                           @RequestParam Integer answer,
                                           @CurrentUser User user) {
        return ApiResponse.success(SuccessCode.GET_QUIZ_RESULT_SUCCESS, this.quizService.checkAnswer(id, answer, user));
    }
}
