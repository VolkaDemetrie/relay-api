package com.volka.relayapi.client.relay;

import com.volka.relayapi.common.dto.StandardResponseDto;
import com.volka.relayapi.vo.RelayResponse;
import org.springframework.core.ParameterizedTypeReference;

public class RelayTypeRef {
    public static ParameterizedTypeReference<StandardResponseDto<RelayResponse>> relayResponseRef = new ParameterizedTypeReference<>() {};
    public static ParameterizedTypeReference<StandardResponseDto<Void>> relayVoidRef = new ParameterizedTypeReference<>() {};
}
