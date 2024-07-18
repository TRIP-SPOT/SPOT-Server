package com.spot.spotserver.common.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int status;
    private final String message;
    @JsonInclude(NON_NULL)
    private T result;

    private ApiResponse() {
        throw new IllegalStateException();
    }

    public static <T> ApiResponse<T> success(SuccessCode success) {
        return new ApiResponse<>(success.getHTTPStatusCode(), success.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessCode success, T result){
        return new ApiResponse<>(success.getHTTPStatusCode(), success.getMessage(), result);
    }

    public static <T> ApiResponse<T> error(ErrorCode error){
        return new ApiResponse<>(error.getHTTPStatusCode(), error.getMessage());
    }

    public static <T> ApiResponse<T> error(ErrorCode error, @Nullable String message){
        return new ApiResponse<>(error.getHTTPStatusCode(), message);
    }

    public static <T> ApiResponse<T> error(ErrorCode error, @Nullable String message, @Nullable T result){
        return new ApiResponse<>(error.getHTTPStatusCode(), message, result);
    }
}
