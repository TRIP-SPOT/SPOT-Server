package com.spot.spotserver.common.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400 BAD REQUEST
    REQUEST_VALIDATION_EXCEPTION(BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_AUTH_CODE(BAD_REQUEST, "인가 코드가 유효하지 않습니다."),
    TOKEN_REQUEST_FAILED(BAD_REQUEST, "액세스 토큰 요청에 실패했습니다."),
    USER_INFO_REQUEST_FAILED(BAD_REQUEST, "사용자 정보 요청에 실패했습니다."),
    USER_CREATION_FAILED(BAD_REQUEST, "사용자 생성에 실패했습니다."),
    INVALID_ACCESS_TOKEN(BAD_REQUEST, "유효하지 않은 액세스 토큰입니다."),
    LIKE_ALREADY_EXIST(BAD_REQUEST, "이미 존재하는 좋아요 입니다."),

    // 401 UNAUTHORIZED
    EMPTY_JWT(UNAUTHORIZED, "JWT 토큰이 비어 있습니다."),
    INVALID_JWT_TOKEN(UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),
    UNSUPPORTED_JWT_TOKEN(UNAUTHORIZED, "지원하지 않는 JWT 토큰 형식입니다."),

    // 403 FORBIDDEN

    // 404 NOT FOUND
    USER_NOT_FOUND(NOT_FOUND, "해당하는 사용자가 존재하지 않습니다."),
    RECORD_NOT_FOUND(NOT_FOUND, "해당하는 기록이 존재하지 않습니다."),
    RECORD_IMAGE_NOT_FOUND(NOT_FOUND, "해당하는 기록 이미지가 존재하지 않습니다."),
    REPRESENTATIVE_IMAGE_NOT_FOUND(NOT_FOUND, "해당하는 대표 이미지가 존재하지 않습니다."),
    SPOT_NOT_FOUND(NOT_FOUND, "해당하는 촬영지가 존재하지 않습니다."),
    LIKE_NOT_FOUND(NOT_FOUND, "해당하는 좋아요가 존재하지 않습니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "S3에 파일 업로드 중 오류가 발생했습니다."),
    FILE_CONVERSION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 변환 중 오류가 발생했습니다."),
    RECORD_IMAGE_PROCESSING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "기록 이미지 처리 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getHTTPStatusCode(){
        return httpStatus.value();
    }
}
