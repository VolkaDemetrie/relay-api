package com.volka.relayapi.controller;

import com.volka.relayapi.client.relay.RelayApiClient;
import com.volka.relayapi.common.dto.StandardResponseDto;
import com.volka.relayapi.service.RelayService;
import com.volka.relayapi.util.Responses;
import com.volka.relayapi.vo.RelayResponse;
import com.volka.relayapi.vo.SaveRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/ext/relay")
@RestController
public class ExternalRelayController {

    private final RelayApiClient relayApiClient;

    @PostMapping
    public Mono<ResponseEntity<StandardResponseDto<Void>>> save(@Valid @RequestBody SaveRequest request) {
        return relayApiClient.save(request);
    }


    @GetMapping("/{relayId}")
    public Mono<ResponseEntity<StandardResponseDto<RelayResponse>>> getById(@PathVariable Long relayId) {
        return relayApiClient.getOne(relayId);
    }

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<RelayResponse> getAll() {
        return relayApiClient.getAll();
    }

    @PatchMapping("/{relayId}")
    public Mono<ResponseEntity<StandardResponseDto<Void>>> modify(
            @PathVariable Long relayId,
            @Valid @RequestBody SaveRequest request
    ) {
        return relayApiClient.modify(relayId, request);
    }

    @DeleteMapping("/{relayId}")
    public Mono<ResponseEntity<StandardResponseDto<Void>>> delete(@PathVariable Long relayId) {
        return relayApiClient.delete(relayId);
    }
}
