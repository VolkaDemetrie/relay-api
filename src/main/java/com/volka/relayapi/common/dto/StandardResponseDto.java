package com.volka.relayapi.common.dto;

import com.volka.relayapi.common.constant.ResponseCode;

public class StandardResponseDto<T> {
    private final String code;
    private final String message;
    private T data;

    private StandardResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private StandardResponseDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private StandardResponseDto(T data) {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getMessage();
        this.data = data;
    }

    public static <T> StandardResponseDto<T> success(T content) {
        return new StandardResponseDto<>(content);
    }

    public static <T> StandardResponseDto<T> successIfEmpty() {
        return new StandardResponseDto<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    public static <T> StandardResponseDto<T> fail(ResponseCode responseCode) {
        return new StandardResponseDto<>(responseCode.getCode(), responseCode.getMessage());
    }

    public static <T> StandardResponseDto<T> fail(String code, String message) {
        return new StandardResponseDto<>(code, message);
    }

    public static <T> StandardResponseDto<T> fail(String code, String message, T content) {
        return new StandardResponseDto<>(code, message, content);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
