package com.tw.pathashala.api.error;

import java.util.Map;

public class ErrorResponse {
    private String message;
    private Map<String, String> reasons;

    public ErrorResponse(String message, Map<String, String> reasons) {
        this.message = message;
        this.reasons = reasons;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getReasons() {
        return reasons;
    }
}
