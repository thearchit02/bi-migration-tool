package com.bimigration.exception;

public class FileParsingException extends RuntimeException {

    private final String errorCode;

    public FileParsingException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}