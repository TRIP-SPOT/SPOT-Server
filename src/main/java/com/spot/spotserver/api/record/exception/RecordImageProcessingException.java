package com.spot.spotserver.api.record.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class RecordImageProcessingException extends BaseException {
    public RecordImageProcessingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
