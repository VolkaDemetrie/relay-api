package com.volka.relayapi.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Setter
@Getter
@ConfigurationProperties("custom-netty")
public class CustomNettyProperties {
    private int readTimeout;
    private int writeTimeout;
    private int bossThreadCount;
    private int workerThreadCount;
    private int selectorCount;

}
