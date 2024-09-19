package com.spot.spotserver.api.auth.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class ExpiredJwtTokenException extends BaseException {
    public ExpiredJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
