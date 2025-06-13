package com.hotela.repository;

import com.hotela.domain.vo.Email;
import com.hotela.model.entity.AuthCredential;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de persistência da entidade AuthCredential.
 */
@Repository
public interface AuthCredentialRepository extends JpaRepository<AuthCredential, UUID> {

    /**
     * Busca uma credencial de autenticação pelo e-mail de login.
     *
     * @param loginEmail o e-mail de login encapsulado em um objeto Email
     * @return um Optional contendo AuthCredential, se encontrado
     */
    Optional<AuthCredential> findByLoginEmail(Email loginEmail);
}
