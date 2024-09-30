package com.spot.spotserver.common.s3.exception;

import com.spot.spotserver.common.advice.BaseException;
import com.spot.spotserver.common.payload.ErrorCode;

public class S3UploadException extends BaseException {
    public S3UploadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
