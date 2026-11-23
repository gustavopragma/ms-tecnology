package com.onclass.ms_tecnology.domain.exception;

public class TechnicalException extends RuntimeException {
    private final String code;

    public TechnicalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public TechnicalException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.code = errorCode.getCode();
    }

    public String getCode() {
        return code;
    }
}
