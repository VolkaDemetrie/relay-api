package com.volka.relayapi.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 테스트용 테이블
 */
@Table("relay")
@Getter
public class Relay {

    @Id
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String path;

    @Builder
    private Relay(Long id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public static Relay createNew(String name, String path) {
        return Relay.builder()
                .name(name)
                .path(path)
                .build();
    }

    public void modify(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
