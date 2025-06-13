package com.hotela.repository;

import com.hotela.model.entity.Room;
import com.hotela.model.enums.RoomType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Optional<Room> findByHotelIdAndRoomNumber(UUID hotelId, String roomNumber);

    List<Room> findByHotelId(UUID hotelId);

    List<Room> findByRoomType(RoomType roomType);
}