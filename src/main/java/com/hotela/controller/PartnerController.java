package com.hotela.controller;

import com.hotela.service.AuthService;
import com.hotela.web.dto.PartnerRegisterRequestDto;
import com.hotela.web.dto.RegistrationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partner") // Define o caminho base http://localhost:8080/partner
public class PartnerController {

    private final AuthService authService;

    public PartnerController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para registrar um novo parceiro. Recebe os dados do parceiro e suas credenciais de
     * acesso.
     *
     * @param registrationDto DTO com os dados para registro do parceiro.
     * @return ResponseEntity com o ID do parceiro criado e mensagem de sucesso, ou erro.
     */
    @PostMapping("/create") // Mapeia para POST http://localhost:8080/partner/create
    public ResponseEntity<RegistrationResponseDto> registerPartner(
            @Valid @RequestBody PartnerRegisterRequestDto registrationDto) {
        RegistrationResponseDto response = authService.registerPartner(registrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Retorna 201 Created
    }
}
