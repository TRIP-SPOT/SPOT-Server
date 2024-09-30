package com.spot.spotserver.api.auth.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class EmptyJwtTokenException extends BaseException {
    public EmptyJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
