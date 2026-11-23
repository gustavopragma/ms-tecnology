package com.onclass.ms_tecnology.domain.exception;

public enum ErrorCode {

    TECHNOLOGY_ALREADY_EXISTS("SE_001", "Technology name already exists"),
    BODY_IS_REQUIRED("SE_002", "Request body is required"),
    VALIDATION_ERROR("SE_003", "Validation error"),
    INTERNAL_SERVER_ERROR("SE_004", "Unexpected internal server error"),
    TECHNOLOGY_NOT_FOUND("SE_005", "One or more technologies do not exist");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
