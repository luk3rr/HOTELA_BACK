package com.hotela.repository;

import com.hotela.model.entity.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para a entidade Booking.
 * Fornece métodos para realizar operações CRUD e consultas personalizadas em dados de Booking.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    /**
     * Busca todas as reservas de um cliente específico.
     * @param customerId o UUID do cliente
     * @return lista de reservas do cliente
     */
    List<Booking> findByCustomerId(UUID customerId);

    /**
     * Busca todas as reservas de um hotel específico.
     * @param hotelId o UUID do hotel
     * @return lista de reservas do hotel
     */
    List<Booking> findByHotelId(UUID hotelId);

    /**
     * Busca todas as reservas de um quarto específico.
     * @param roomId o UUID do quarto
     * @return lista de reservas do quarto
     */
    List<Booking> findByRoomId(UUID roomId);

    /**
     * Busca uma reserva pelo seu ID e pelo ID do cliente.
     * @param bookingId o UUID da reserva
     * @param customerId o UUID do cliente
     * @return um Optional contendo a reserva se encontrada, ou vazio caso contrário
     */
    Optional<Booking> findByIdAndCustomerId(UUID bookingId, UUID customerId);
}