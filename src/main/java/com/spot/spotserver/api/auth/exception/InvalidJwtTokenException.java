package com.spot.spotserver.api.auth.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class InvalidJwtTokenException extends BaseException {
    public InvalidJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
