package com.spot.spotserver.api.auth.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class UnsupportedJwtTokenException extends BaseException {
    public UnsupportedJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
