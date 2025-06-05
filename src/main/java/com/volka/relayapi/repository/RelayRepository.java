package com.volka.relayapi.repository;

import com.volka.relayapi.model.Relay;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayRepository extends ReactiveCrudRepository<Relay, Long> {
}
