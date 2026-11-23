package com.onclass.ms_tecnology.domain.exception;

public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.code = errorCode.getCode();
    }
    public String getCode() {
        return code;
    }
}
