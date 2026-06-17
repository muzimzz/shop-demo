package com.ureca.shopdemo.global.exception;

import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{

    private final int statusCode;

    public GeneralException (ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatusCode();
    }
}
