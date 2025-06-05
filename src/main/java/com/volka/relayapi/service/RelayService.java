package com.volka.relayapi.service;

import com.volka.relayapi.model.Relay;
import com.volka.relayapi.repository.RelayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class RelayService {

    private final RelayRepository repository;

    public Mono<Relay> getRelayById(Long id) {
        return repository.findById(id);
    }

    public Flux<Relay> getAllRelays() {
        return repository.findAll();
    }

    public Mono<Long> save(String name, String path) {
        return repository.save(Relay.createNew(name, path))
                .map(Relay::getId);
    }

    public Mono<Long> modify(Long id, String name, String path) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Not found")))
                .flatMap(record -> {
                    record.modify(name, path);
                    return repository.save(record)
                            .map(Relay::getId);
                });
    }

    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
