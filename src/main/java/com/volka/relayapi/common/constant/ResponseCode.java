package com.volka.relayapi.common.constant;

public enum ResponseCode {
    SUCCESS("SUCCESS", "SUCCESS"),
    FAIL("COE0000", "FAIL"),
    VALID_FAIL("COE0001", "VALID_FAIL")
    ;

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
