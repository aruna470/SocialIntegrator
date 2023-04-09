package com.eyerubic.socialintegrator.exception;

/**
 * This class prepares error responses
 */
public class ErrorResponse {
    private int code;
    private String reason;
    private String attribute;

    public ErrorResponse(int code, String reason, String attribute) {
        this.code = code;
        this.reason = reason;
        this.attribute = attribute;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getAttribute() {
        return attribute;
    }
}
