package com.hotela.repository;

import com.hotela.domain.vo.Cnpj;
import com.hotela.model.entity.AuthCredential;
import com.hotela.model.entity.Partner;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @details Repositório para a entidade Partner.
 */
@Repository
public interface PartnerRepository extends JpaRepository<Partner, UUID> {

    /**
     * @details Busca um Partner pelo seu CNPJ. Como CNPJ é único para Partner, este método retorna
     *     um Optional.
     * @param cnpj O CNPJ (Value Object) do parceiro.
     * @return Um Optional contendo o Partner se encontrado, caso contrário, um Optional vazio.
     */
    Optional<Partner> findByCnpj(Cnpj cnpj);

    /**
     * @details Busca um Partner pela sua AuthCredential associada.
     * @param authCredential A credencial de autenticação do parceiro.
     * @return Um Optional contendo o Partner se encontrado, caso contrário, um Optional vazio.
     */
    Optional<Partner> findByAuthCredential(AuthCredential authCredential);

    /**
     * @details Busca um Partner pelo ID da sua AuthCredential associada.
     * @param authCredentialId O UUID da credencial de autenticação.
     * @return Um Optional contendo o Partner se encontrado, caso contrário, um Optional vazio.
     */
    Optional<Partner> findByAuthCredential_Id(UUID authCredentialId);
}
