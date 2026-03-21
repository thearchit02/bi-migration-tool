package com.bimigration.exception;

public class MigrationException extends RuntimeException {

    private final String errorCode;

    public MigrationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}