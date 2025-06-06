package com.volka.relayapi.vo;

import com.volka.relayapi.entity.Relay;

public record RelayResponse(
        Long id,
        String name,
        String path
) {

    public static RelayResponse from(Relay relay) {
        return new RelayResponse(relay.getId(), relay.getName(), relay.getPath());
    }
}
