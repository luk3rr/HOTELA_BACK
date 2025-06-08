package com.hotela.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

// Você precisará do seu enum PartnerStatus se quiser recebê-lo no DTO,
// caso contrário, ele pode ser definido com um default no serviço.
// import com.hotela.model.enums.PartnerStatus;

public record PartnerRegisterRequestDto(
        @NotBlank(message = "Company name cannot be blank") @Size(max = 255) String companyName,
        @Size(max = 255) // Legal name é opcional na entidade
                String legalName,
        @NotBlank(message = "CNPJ cannot be blank")
                @Size(max = 18) // Formato "XX.XXX.XXX/XXXX-XX" ou 14 para só números
                String cnpj,
        @NotBlank(message = "Primary contact email cannot be blank")
                @Email(message = "Primary contact email should be valid")
                @Size(max = 254)
                String primaryContactEmail, // Para contato e login
        @NotBlank(message = "Password cannot be blank")
                @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
                String password,
        @NotBlank(message = "Primary phone cannot be blank") @Size(max = 18) String primaryPhone,
        @NotNull(message = "Address cannot be null") @Valid AddressDto address,
        @Size(max = 255) String representativeName, // Opcional
        @Email(message = "Representative email should be valid") @Size(max = 254)
                String representativeEmail, // Opcional
        @Size(max = 18) String representativePhone, // Opcional
        LocalDate contractSignedAt, // Opcional

        // PartnerStatus status, // Se for definido no registro, senão default no serviço

        String notes // Opcional
        ) {}
