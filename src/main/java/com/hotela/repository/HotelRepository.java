package com.hotela.repository;

import com.hotela.model.entity.Hotel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de acesso a dados da entidade Hotel.
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    /**
     * Busca um hotel pelo nome.
     *
     * @param name nome do hotel
     * @return um Optional contendo o hotel, se encontrado
     */
    Optional<Hotel> findByName(String name);

    /**
     * Busca todos os hotéis associados a um parceiro específico.
     *
     * @param partnerId identificador do parceiro
     * @return lista de hotéis do parceiro
     */
    List<Hotel> findByPartnerId(UUID partnerId);
}