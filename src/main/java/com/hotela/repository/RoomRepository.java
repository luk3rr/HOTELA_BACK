package com.hotela.repository;

import com.hotela.model.entity.Room;
import com.hotela.model.enums.RoomType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface de repositório para a entidade Room.
 * Fornece métodos para realizar operações CRUD e consultas personalizadas em dados de Room.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    /**
     * Busca um quarto pelo ID do hotel e número do quarto.
     *
     * @param hotelId o UUID do hotel
     * @param roomNumber o número do quarto
     * @return um Optional contendo o Room se encontrado, ou vazio caso contrário
     */
    Optional<Room> findByHotelIdAndRoomNumber(UUID hotelId, String roomNumber);

    /**
     * Busca todos os quartos pertencentes a um hotel específico.
     *
     * @param hotelId o UUID do hotel
     * @return uma lista de entidades Room
     */
    List<Room> findByHotelId(UUID hotelId);

    /**
     * Busca todos os quartos de um tipo específico.
     *
     * @param roomType o tipo do quarto
     * @return uma lista de entidades Room
     */
    List<Room> findByRoomType(RoomType roomType);
}