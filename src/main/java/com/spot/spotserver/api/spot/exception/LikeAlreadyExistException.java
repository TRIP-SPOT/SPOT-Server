package com.spot.spotserver.api.spot.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class LikeAlreadyExistException extends BaseException {
    public LikeAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}