package com.spot.spotserver.common.advice;

import com.spot.spotserver.common.payload.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode error;

    public BaseException(ErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public int getHttpStatus(){
        return error.getHTTPStatusCode();
    }
}
