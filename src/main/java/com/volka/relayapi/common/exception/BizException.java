package com.volka.relayapi.common.exception;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

/**
 * 비즈니스 예외
 */
public class BizException extends RuntimeException {
    @NotBlank
    private final String code;

    @Nullable
    private Object[] extraArgs;

    public BizException(String code) {
        this.code = code;
    }

    public BizException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BizException(String code, Object[] extraArgs) {
        this.code = code;
        this.extraArgs = extraArgs;
    }

    public BizException(String code, Throwable cause, Object[] extraArgs) {
        super(cause);
        this.code = code;
        this.extraArgs = extraArgs;
    }

    public String getCode() {
        return code;
    }

    @Nullable
    public Object[] getExtraArgs() {
        return extraArgs;
    }
}
