package com.hotela.repository;

import com.hotela.model.entity.Room;
import com.hotela.model.enums.RoomType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Room entity.
 * Provides methods to perform CRUD operations and custom queries on Room data.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    /**
     * Finds a room by hotel ID and room number.
     *
     * @param hotelId the UUID of the hotel
     * @param roomNumber the room number
     * @return an Optional containing the Room if found, or empty otherwise
     */
    Optional<Room> findByHotelIdAndRoomNumber(UUID hotelId, String roomNumber);

    /**
     * Finds all rooms belonging to a specific hotel.
     *
     * @param hotelId the UUID of the hotel
     * @return a list of Room entities
     */
    List<Room> findByHotelId(UUID hotelId);

    /**
     * Finds all rooms of a specific room type.
     *
     * @param roomType the type of the room
     * @return a list of Room entities
     */
    List<Room> findByRoomType(RoomType roomType);
}