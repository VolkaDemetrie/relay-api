package com.volka.relayapi.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;

/**
 * 로깅 필터
 */
@Slf4j
@Component
public class LoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Instant start = Instant.now();

        ServerHttpRequest request = exchange.getRequest();

        log.info("[REQUEST] :: {} {}{}", request.getMethod(), request.getPath().pathWithinApplication().value(), request.getURI().getRawQuery());

        return chain.filter(exchange)
                .doOnSuccess(done -> {
                    loggingResponse(exchange, start);
                })
                .doOnError(error -> {
                    log.error("[LOGGIGN_ERROR] :: {}", error.getMessage());
                    loggingResponse(exchange, start);
                });
    }

    private void loggingResponse(ServerWebExchange exchange, Instant start) {
        ServerHttpResponse response = exchange.getResponse();
        Duration duration = Duration.between(start, Instant.now());

        log.info("[RESPONSE] :: STATUS:{} DURATION:{}ms", response.getStatusCode(), duration.toMillis());
    }
}
