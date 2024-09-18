package com.spot.spotserver.common.s3.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class FileConversionException extends BaseException {
    public FileConversionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
