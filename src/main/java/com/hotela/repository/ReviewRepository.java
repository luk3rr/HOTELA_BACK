package com.hotela.repository;

import com.hotela.model.entity.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para acessar entidades Review no banco de dados.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    /**
     * Busca todas as avaliações de um determinado hotel pelo seu ID.
     *
     * @param hotelId o UUID do hotel
     * @return uma lista de avaliações para o hotel especificado
     */
    List<Review> findByHotelId(UUID hotelId);

    /**
     * Busca todas as avaliações feitas por um cliente específico.
     *
     * @param customerId o UUID do cliente
     * @return uma lista de avaliações feitas pelo cliente especificado
     */
    List<Review> findByCustomerId(UUID customerId);

    /**
     * Busca uma avaliação associada a uma reserva específica.
     *
     * @param bookingId o UUID da reserva
     * @return um Optional contendo a avaliação, se encontrada, ou vazio caso contrário
     */
    Optional<Review> findByBookingId(UUID bookingId);
}