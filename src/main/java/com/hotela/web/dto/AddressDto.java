package com.hotela.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressDto(
        @NotBlank(message = "Street address cannot be blank") @Size(max = 255) String streetAddress,
        @Size(max = 20) String number,
        @Size(max = 100) String complement,
        @Size(max = 100) String neighborhood,
        @NotBlank(message = "City cannot be blank") @Size(max = 100) String city,
        @NotBlank(message = "State/Province cannot be blank") @Size(max = 100) String stateProvince,
        @NotBlank(message = "Postal code cannot be blank")
                @Size(max = 10) // Ajuste conforme necessidade do formato do
                // CEP
                String postalCode,
        @Size(min = 2, max = 2, message = "Country code must be 2 characters")
                String countryCode // Opcional, pode ter
        // um valor padrão
        // "BR" no serviço
        ) {}
