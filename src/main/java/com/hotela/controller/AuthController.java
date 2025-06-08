package com.hotela.controller;

import com.hotela.service.AuthService;
import com.hotela.web.dto.LoginRequestDto;
import com.hotela.web.dto.LoginResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Define o caminho base http://localhost:8080/api/auth
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Endpoint para login de clientes.
     *
     * @param loginRequestDto DTO com email e senha. POST
     *     http://localhost:8080/api/auth/customer/login
     * @return ResponseEntity com o token JWT e informações de expiração, ou erro.
     */
    @PostMapping("/customer/login")
    public ResponseEntity<LoginResponseDto> loginCustomer(
            @Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.loginUser(loginRequestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para login de parceiros.
     *
     * @param loginRequestDto DTO com email e senha. POST
     *     http://localhost:8080/api/auth/partner/login
     * @return ResponseEntity com o token JWT e informações de expiração, ou erro.
     */
    @PostMapping("/partner/login")
    public ResponseEntity<LoginResponseDto> loginPartner(
            @Valid @RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.loginUser(loginRequestDto);
        return ResponseEntity.ok(response); // Retorna 200 OK com o token
    }

    /**
     * Endpoint para logout de clientes. POST http://localhost:8080/api/auth/customer/logout
     * Atualmente, apenas retorna uma mensagem de sucesso.
     *
     * @return ResponseEntity com mensagem de sucesso.
     */
    @PostMapping("/customer/logout")
    public ResponseEntity<String> logoutCustomer() {

        return ResponseEntity.ok("{\"message\": \"Logout successful\"}");
    }

    /**
     * Endpoint para logout de parceiros. POST http://localhost:8080/api/auth/partner/logout
     *
     * @return ResponseEntity com mensagem de sucesso.
     */
    @PostMapping("/partner/logout")
    public ResponseEntity<String> logoutPartner() {
        return ResponseEntity.ok("{\"message\": \"Logout successful\"}");
    }
}
