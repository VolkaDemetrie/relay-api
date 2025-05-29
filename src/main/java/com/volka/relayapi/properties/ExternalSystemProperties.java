package com.volka.relayapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 외부 시스템 연동 설정 정보
 */
@ConfigurationProperties("external-system")
public record ExternalSystemProperties(

) {
}
