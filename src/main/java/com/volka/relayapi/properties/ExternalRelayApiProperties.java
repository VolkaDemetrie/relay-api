package com.volka.relayapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 외부 시스템 연동 설정 정보
 */
@ConfigurationProperties("external.relay")
public record ExternalRelayApiProperties(
        String baseUrl,
        Uris uris
) {
    public record Uris(
            String getOnePath,
            String getAllPath,
            String savePath,
            String modifyPath,
            String deletePath
    ) {}
}
