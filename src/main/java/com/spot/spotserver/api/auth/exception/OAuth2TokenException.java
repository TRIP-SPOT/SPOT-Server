package com.spot.spotserver.api.auth.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class OAuth2TokenException extends BaseException {
    public OAuth2TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
