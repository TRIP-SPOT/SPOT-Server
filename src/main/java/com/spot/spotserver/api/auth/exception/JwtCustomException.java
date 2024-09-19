package com.spot.spotserver.api.auth.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class JwtCustomException extends BaseException {
    public JwtCustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public JwtCustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}
