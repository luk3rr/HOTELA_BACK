package com.hotela.repository;

import com.hotela.model.entity.Address;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @details Repositório para a entidade Address, fornecendo operações de CRUD.
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {}
