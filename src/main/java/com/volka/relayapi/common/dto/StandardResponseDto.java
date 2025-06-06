package com.volka.relayapi.common.dto;

import com.volka.relayapi.common.constant.ResponseCode;

public record StandardResponseDto<T>(
        String code,
        String message,
        T data
) {
    public static <T> StandardResponseDto<T> success(T content) {
        return new StandardResponseDto<>(ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.message(), content);
    }

    public static <T> StandardResponseDto<T> successIfEmpty() {
        return new StandardResponseDto<>(ResponseCode.SUCCESS.code(), ResponseCode.SUCCESS.message(), null);
    }

    public static <T> StandardResponseDto<T> fail(ResponseCode responseCode) {
        return new StandardResponseDto<>(responseCode.code(), responseCode.message(), null);
    }

    public static <T> StandardResponseDto<T> fail(String code, String message) {
        return new StandardResponseDto<>(code, message, null);
    }

    public static <T> StandardResponseDto<T> fail(String code, String message, T content) {
        return new StandardResponseDto<>(code, message, content);
    }
}
