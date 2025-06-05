package com.volka.relayapi.common.constant;

public enum ResponseCode {
    SUCCESS("SUCCESS", "SUCCESS"),
    FAIL("COE0000", "FAIL")
    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
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
