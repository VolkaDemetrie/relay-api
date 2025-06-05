package com.volka.relayapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/relay")
@RestController
public class RelayController {


    @GetMapping("/mono-test")
    Mono<String> getMonoTest() {
        return Mono.just("test").doOnNext(data -> System.out.println("call test"));
    }

}
