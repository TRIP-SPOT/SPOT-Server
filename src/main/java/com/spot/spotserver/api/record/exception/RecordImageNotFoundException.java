package com.spot.spotserver.api.record.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class RecordImageNotFoundException extends BaseException {
    public RecordImageNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
