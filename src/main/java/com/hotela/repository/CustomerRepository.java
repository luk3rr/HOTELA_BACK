package com.hotela.repository;

import com.hotela.model.entity.AuthCredential;
import com.hotela.model.entity.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @details Repositório para a entidade Customer.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    /**
     * @details Busca um Customer pela sua AuthCredential associada.
     * @param authCredential A credencial de autenticação do cliente.
     * @return Um Optional contendo o Customer se encontrado, caso contrário, um Optional vazio.
     */
    Optional<Customer> findByAuthCredential(AuthCredential authCredential);

    /**
     * @details Busca um Customer pelo ID da sua AuthCredential associada. É útil se você já tem o
     *     ID da credencial e quer o cliente correspondente.
     * @param authCredentialId O UUID da credencial de autenticação.
     * @return Um Optional contendo o Customer se encontrado, caso contrário, um Optional vazio.
     */
    Optional<Customer> findByAuthCredential_Id(UUID authCredentialId);
}
