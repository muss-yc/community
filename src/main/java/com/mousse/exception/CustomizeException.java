package com.mousse.exception;

/**
 * @author mousse
 * @data 2021/8/30
 */
public class CustomizeException extends RuntimeException {
    private final String message;
    private final Integer code;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }


    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
