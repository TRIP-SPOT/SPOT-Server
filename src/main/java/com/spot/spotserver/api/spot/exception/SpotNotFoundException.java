package com.spot.spotserver.api.spot.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class SpotNotFoundException extends BaseException {
    public SpotNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
