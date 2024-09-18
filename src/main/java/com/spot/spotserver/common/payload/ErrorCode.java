package com.spot.spotserver.common.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 400 BAD REQUEST
    REQUEST_VALIDATION_EXCEPTION(BAD_REQUEST, "잘못된 요청입니다."),

    // 401 UNAUTHORIZED

    // 403 FORBIDDEN

    // 404 NOT FOUND
    RECORD_NOT_FOUND(NOT_FOUND, "해당하는 기록이 존재하지 않습니다."),
    RECORD_IMAGE_NOT_FOUND(NOT_FOUND, "해당하는 기록 이미지가 존재하지 않습니다."),
    REPRESENTATIVE_IMAGE_NOT_FOUND(NOT_FOUND, "해당하는 대표 이미지가 존재하지 않습니다."),

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
