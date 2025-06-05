package com.hotela.repository;

import com.hotela.domain.vo.Email;
import com.hotela.model.entity.AuthCredential;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthCredentialRepository extends JpaRepository<AuthCredential, UUID> {

    Optional<AuthCredential> findByLoginEmail(Email loginEmail);
}
