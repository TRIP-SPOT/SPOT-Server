package com.spot.spotserver.api.spot.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class WorkNotFoundException extends BaseException {
    public WorkNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
