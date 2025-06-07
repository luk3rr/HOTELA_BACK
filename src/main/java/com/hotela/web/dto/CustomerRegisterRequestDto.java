package com.hotela.web.dto;

import com.hotela.model.enums.DocumentType; // Importe seu enum
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CustomerRegisterRequestDto(
        @NotBlank(message = "Full name cannot be blank") @Size(max = 255) String fullName,
        @NotBlank(message = "Email cannot be blank")
                @Email(message = "Email should be valid")
                @Size(max = 254)
                String email, // Para contato e login
        @NotBlank(message = "Password cannot be blank")
                @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
                String password,
        @NotBlank(message = "Primary phone cannot be blank")
                @Size(max = 18) // Ajuste conforme seu VO Telephone espera
                String primaryPhone,
        @NotNull(message = "Birth date cannot be null")
                @Past(message = "Birth date must be in the past")
                LocalDate birthDate,
        @NotNull(message = "Document type cannot be null") DocumentType documentIdType,
        @NotBlank(message = "Document value cannot be blank") @Size(max = 50)
                String documentIdValue,

        // O email secundário é opcional na sua entidade Customer
        @Email(message = "Secondary contact email should be valid") @Size(max = 254)
                String contactEmailSecondary, // Opcional
        @Valid // Para validar os campos dentro de AddressDto
                @NotNull(
                        message =
                                "Main address cannot be null") // Ou pode ser opcional dependendo da
                // regra
                AddressDto mainAddress) {}
