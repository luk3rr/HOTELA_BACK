package com.hotela.web.dto;

import java.util.UUID;

public record RegistrationResponseDto(
        UUID id, // ID do Customer ou Partner criado
        String message) {}
