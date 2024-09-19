package com.spot.spotserver.api.record.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class RecordNotFoundException extends BaseException {
    public RecordNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
