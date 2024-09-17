package com.spot.spotserver.api.quiz.controller;

import com.spot.spotserver.api.quiz.dto.AnswerCheckRequest;
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

    @GetMapping("/")
    public ApiResponse<QuizResponse> getQuiz(Long id) {
        return ApiResponse.success(SuccessCode.GET_QUIZ_SUCCESS, this.quizService.getQuiz(id));
    }

    @PostMapping("/answer")
    public ApiResponse<AnswerCheckResponse> checkAnswer(@RequestBody @Valid AnswerCheckRequest answerCheckRequest,
                                           @CurrentUser User user) {
        return ApiResponse.success(SuccessCode.GET_QUIZ_RESULT_SUCCESS, this.quizService.checkAnswer(answerCheckRequest, user));
    }
}
