package com.volka.relayapi.client.relay;

import com.volka.relayapi.common.dto.StandardResponseDto;
import com.volka.relayapi.properties.ExternalRelayApiProperties;
import com.volka.relayapi.vo.RelayResponse;
import com.volka.relayapi.vo.SaveRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@Component
public class RelayApiClient {

    private final WebClient relayClient;
    private final ExternalRelayApiProperties relayApiProperties;

    RelayApiClient(@Qualifier("relayClient") WebClient relayClient, ExternalRelayApiProperties relayApiProperties) {
        this.relayClient = relayClient;
        this.relayApiProperties = relayApiProperties;
    }


    public Mono<ResponseEntity<StandardResponseDto<RelayResponse>>> getOne(Long relayId) {
        return relayClient.get()
                .uri(relayApiProperties.uris().getOnePath(), Map.of("relayId", relayId))
                .retrieve()
                .toEntity(RelayTypeRef.relayResponseRef);
    }

    public Flux<RelayResponse> getAll() {
        return relayClient.get()
                .uri(relayApiProperties.uris().getAllPath())
                .retrieve()
                .bodyToFlux(RelayResponse.class);
    }

    public Mono<ResponseEntity<StandardResponseDto<Void>>> save(SaveRequest request) {
        return relayClient.post()
                .uri(relayApiProperties.uris().savePath())
                .bodyValue(request)
                .retrieve()
                .toEntity(RelayTypeRef.relayVoidRef);
    }

    public Mono<ResponseEntity<StandardResponseDto<Void>>> modify(Long relayId, SaveRequest request) {
        return relayClient.patch()
                .uri(relayApiProperties.uris().modifyPath(), Map.of("relayId", relayId))
                .bodyValue(request)
                .retrieve()
                .toEntity(RelayTypeRef.relayVoidRef);
    }

    public Mono<ResponseEntity<StandardResponseDto<Void>>> delete(Long relayId) {
        return relayClient.delete()
                .uri(relayApiProperties.uris().deletePath(), Map.of("relayId", relayId))
                .retrieve()
                .toEntity(RelayTypeRef.relayVoidRef);
    }
}
