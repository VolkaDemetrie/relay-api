package com.volka.relayapi;

import com.volka.relayapi.properties.CustomNettyProperties;
import com.volka.relayapi.properties.ExternalRelayApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({CustomNettyProperties.class, ExternalRelayApiProperties.class})
@SpringBootApplication
public class RelayApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RelayApiApplication.class, args);
    }

}
