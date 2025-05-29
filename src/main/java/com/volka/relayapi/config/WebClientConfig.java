package com.volka.relayapi.config;

import com.volka.relayapi.properties.ExternalSystemProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfig {

    private final ExternalSystemProperties externalSystemProperties;
    private static final int MAX_CONNECTIONS = 4000;
    private static final int PENDING_ACQUIRE_MAX_COUNT = 1000;

    public WebClientConfig(ExternalSystemProperties externalSystemProperties) {
        this.externalSystemProperties = externalSystemProperties;
    }

    @Bean
    public WebClient relayClient() {

        ConnectionProvider connProvider = ConnectionProvider.builder("conn-pool")
                .maxConnectionPools(MAX_CONNECTIONS)
                .pendingAcquireMaxCount(PENDING_ACQUIRE_MAX_COUNT)
                .pendingAcquireTimeout(Duration.ofSeconds(2))
                .build();

        HttpClient httpClient = HttpClient.create(connProvider)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .responseTimeout(Duration.ofSeconds(10))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10))
                        .addHandlerLast(new WriteTimeoutHandler(10))
                );

        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
    }
}
