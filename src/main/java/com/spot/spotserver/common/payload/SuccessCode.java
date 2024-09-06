package com.spot.spotserver.common.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    // 200 OK
    GET_SPOT_SUCCESS(OK, "촬영지 조회에 성공하였습니다."),
    LOGIN_SUCCESS(OK, "로그인에 성공하였습니다"),
    REISSUE_TOKEN_SUCCESS(OK, "토큰 재발급에 성공하였습니다."),
    REGISTER_NICKNAME_SUCCESS(OK, "닉네임이 정상적으로 설정되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode() {
        return httpStatus.value();
    }
}
