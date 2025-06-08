package com.hotela.controller;

import com.hotela.service.AuthService;
import com.hotela.web.dto.CustomerRegisterRequestDto;
import com.hotela.web.dto.RegistrationResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer") // Define o caminho base http://localhost:8080/customer
public class CustomerController {

    private final AuthService authService;

    // Injeção de dependência do AuthService via construtor
    public CustomerController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para registrar um novo cliente. Recebe os dados do cliente e suas credenciais de
     * acesso.
     *
     * @param registrationDto DTO com os dados para registro do cliente.
     * @return ResponseEntity com o ID do cliente criado e mensagem de sucesso, ou erro.
     */
    @PostMapping("/create") // Mapeia para POST http://localhost:8080/customer/create
    public ResponseEntity<RegistrationResponseDto> registerCustomer(
            @Valid @RequestBody CustomerRegisterRequestDto registrationDto) {
        RegistrationResponseDto response = authService.registerCustomer(registrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // Retorna 201 Created
    }
}
