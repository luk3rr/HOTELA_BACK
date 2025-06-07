package com.hotela.security.service;

import com.hotela.model.entity.AuthCredential;
import com.hotela.model.enums.AuthUserType; // Importar seu enum
import java.util.Collection;
import java.util.Collections; // Para criar uma lista imutável de uma única autoridade
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @details Implementação de UserDetails que encapsula nossa entidade AuthCredential. Fornece ao
 *     Spring Security as informações necessárias sobre o usuário autenticado.
 */
public class CustomUserDetails implements UserDetails {

    private final AuthCredential authCredential;

    public CustomUserDetails(AuthCredential authCredential) {
        this.authCredential = authCredential;
    }

    /**
     * @details Retorna as autoridades (papéis/roles) concedidas ao usuário. Mapeamos nosso
     *     AuthUserType para um SimpleGrantedAuthority prefixado com "ROLE_". Ex: Se userType for
     *     CUSTOMER, a autoridade será "ROLE_CUSTOMER".
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // O Spring Security espera que os papéis comecem com "ROLE_" por convenção
        // para algumas de suas funcionalidades (como
        // @PreAuthorize("hasRole('CUSTOMER')")).
        String role = "ROLE_" + authCredential.getUserType().name();
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return authCredential.getPasswordHash();
    }

    /**
     * @details Retorna o nome de usuário usado para autenticar o usuário. No nosso caso, é o
     *     loginEmail.
     */
    @Override
    public String getUsername() {
        return authCredential.getLoginEmail().getValue(); // Usamos o valor do VO Email
    }

    /**
     * @details Indica se o usuário está habilitado ou desabilitado. Usamos o campo 'isActive' da
     *     nossa entidade AuthCredential.
     */
    @Override
    public boolean isEnabled() {
        return authCredential.isActive();
    }

    // Métodos adicionais para acessar dados da entidade
    public UUID getId() {
        return authCredential.getId();
    }

    public AuthUserType getUserType() {
        return authCredential.getUserType();
    }

    public AuthCredential getAuthCredential() {
        return authCredential;
    }
}
