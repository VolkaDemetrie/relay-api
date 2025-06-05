package com.volka.relayapi.util;

import com.volka.relayapi.common.dto.StandardResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 컨트롤러 응답 제어 유틸리티
 */
public class Responses {

    private Responses(){}

    public static <T> ResponseEntity<StandardResponseDto<T>> wrap(T data) {
        return ResponseEntity.ok(StandardResponseDto.success(data));
    }

    public static ResponseEntity<StandardResponseDto<Void>> empty() {
        return ResponseEntity.ok(StandardResponseDto.successIfEmpty());
    }

    public static <T> Mono<ResponseEntity<StandardResponseDto<Void>>> created(T id, String baseLocation) {
        return Mono.just(ResponseEntity
                .created(URI.create(String.format("%s/%s", baseLocation, id)))
                .body(StandardResponseDto.successIfEmpty()));
    }

    public static <T> Mono<ResponseEntity<StandardResponseDto<T>>> ok(T data) {
        return Mono.just(wrap(data));
    }

    public static <T> Mono<ResponseEntity<StandardResponseDto<T>>> from(Mono<T> mono) {
        return mono.map(Responses::wrap);
    }
}
