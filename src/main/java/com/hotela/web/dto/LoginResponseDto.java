package com.hotela.web.dto;

import java.time.OffsetDateTime; // Ou String, dependendo de como você quer formatar

public record LoginResponseDto(
        String token,
        String type, // Geralmente "Bearer"
        OffsetDateTime expireAt // Ou String expireAt
        ) {
    public LoginResponseDto(String token, OffsetDateTime expireAt) {
        this(token, "Bearer", expireAt);
    }
}
