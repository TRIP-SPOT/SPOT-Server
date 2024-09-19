package com.spot.spotserver.api.record.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class RepresentativeImageNotFoundException extends BaseException {
    public RepresentativeImageNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
