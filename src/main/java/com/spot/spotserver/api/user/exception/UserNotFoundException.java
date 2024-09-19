package com.spot.spotserver.api.user.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
