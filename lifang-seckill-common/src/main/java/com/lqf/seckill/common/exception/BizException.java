package com.lqf.seckill.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public BizException(BaseExceptionInterface baseException) {
        this.errorCode = baseException.getErrorCode();
        this.errorMessage = baseException.getErrorMessage();
    }
}
