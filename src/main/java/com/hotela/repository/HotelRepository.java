package com.hotela.repository;

import com.hotela.model.entity.Hotel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {
    Optional<Hotel> findByName(String name);

    List<Hotel> findByPartnerId(UUID partnerId);
}