package com.bimigration.exception;

public class AIConversionException extends RuntimeException {

    private final String errorCode;

    public AIConversionException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}