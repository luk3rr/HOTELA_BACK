package com.hotela.security.service;

import com.hotela.domain.vo.Email;
import com.hotela.model.entity.AuthCredential;
import com.hotela.repository.AuthCredentialRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @details Serviço para carregar detalhes do usuário (AuthCredential) para o Spring Security. Ele
 *     busca o usuário no banco de dados pelo email de login.
 */
@Service // Marca esta classe como um serviço Spring, tornando-a um bean gerenciável.
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthCredentialRepository authCredentialRepository;

    public CustomUserDetailsService(AuthCredentialRepository authCredentialRepository) {
        this.authCredentialRepository = authCredentialRepository;
    }

    /**
     * @details Carrega o usuário pelo seu "username" (email). Este método é chamado pelo Spring
     *     Security durante o processo de autenticação.
     * @param username O email fornecido para login.
     * @return Um objeto UserDetails contendo as informações do usuário.
     * @throws UsernameNotFoundException se o usuário com o email fornecido não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // O "username" aqui é o email que o usuário digita no login.
        // Precisamos convertê-lo para o nosso VO Email para usar no repositório.
        Email loginEmail;
        try {
            loginEmail = new Email(username);
        } catch (IllegalArgumentException e) {
            // Se o formato do email for inválido, o usuário não será encontrado.
            throw new UsernameNotFoundException("Invalid email format: " + username, e);
        }

        AuthCredential credential =
                authCredentialRepository
                        .findByLoginEmail(loginEmail)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "User not found with email: " + username));

        return new CustomUserDetails(credential);
    }
}
