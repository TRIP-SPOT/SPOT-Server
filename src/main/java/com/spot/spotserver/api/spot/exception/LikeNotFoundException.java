package com.spot.spotserver.api.spot.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class LikeNotFoundException extends BaseException {
    public LikeNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }
}
