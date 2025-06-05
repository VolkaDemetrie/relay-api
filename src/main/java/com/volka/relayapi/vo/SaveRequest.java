package com.volka.relayapi.vo;

import jakarta.validation.constraints.NotBlank;

/**
 * 저장 요청 VO
 * @param name
 * @param path
 */
public record SaveRequest(
        @NotBlank
        String name,

        @NotBlank
        String path
) {
}
