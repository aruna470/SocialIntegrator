package com.eyerubic.socialintegrator.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    
    private final String attribute;
    private final int code;
    private final String message;
    private final String data;
    private HttpStatus httpStatus; //NOSONAR

    public CustomException(String attribute, int code, String message, String data) {
        super(message);

        this.attribute = attribute;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public CustomException(String attribute, int code, String message, String data, HttpStatus httpStatus) {
        super(message);

        this.attribute = attribute;
        this.code = code;
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public String getAttribute() {
        return attribute;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
