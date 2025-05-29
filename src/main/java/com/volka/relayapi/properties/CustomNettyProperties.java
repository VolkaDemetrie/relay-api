package com.volka.relayapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("custom-netty")
public record CustomNettyProperties(
        int readTimeout,
        int writeTimeout,
        int workerThreadCount,
        int selectorCount,
        boolean daemonThread
) {}

