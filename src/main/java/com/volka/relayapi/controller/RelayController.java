package com.volka.relayapi.controller;

import com.volka.relayapi.common.dto.StandardResponseDto;
import com.volka.relayapi.service.RelayService;
import com.volka.relayapi.util.Responses;
import com.volka.relayapi.vo.RelayResponse;
import com.volka.relayapi.vo.SaveRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/relay")
@RestController
public class RelayController {

    private final RelayService relayService;

    @GetMapping("/mono-test")
    public Mono<String> getMonoTest() {
        return Mono.just("test").doOnNext(data -> System.out.println("call test"));
    }


    @PostMapping("/save")
    public Mono<ResponseEntity<StandardResponseDto<Void>>> save(@Valid @RequestBody SaveRequest request) {
        return relayService.save(request.name(), request.path())
                .flatMap(id -> Responses.created(id, "/api/v1/relay"));
    }


    @GetMapping("/{relayId}")
    public Mono<ResponseEntity<StandardResponseDto<RelayResponse>>> getById(@PathVariable Long relayId) {
        return relayService.getRelayById(relayId)
                .map(record -> Responses.wrap(RelayResponse.from(record)));
    }

    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<RelayResponse> getAll() {
        return relayService.getAllRelays()
                .map(RelayResponse::from);
    }

    @PatchMapping("/{relayId}")
    public Mono<ResponseEntity<StandardResponseDto<Void>>> modify(
            @PathVariable Long relayId,
            @Valid @RequestBody SaveRequest request
    ) {
        return relayService.modify(relayId, request.name(), request.path())
                .thenReturn(Responses.empty());
    }

    @DeleteMapping("/{relayId}")
    public Mono<ResponseEntity<StandardResponseDto<Void>>> delete(@PathVariable Long relayId) {
        return relayService.deleteById(relayId)
                .thenReturn(Responses.empty());
    }
}
