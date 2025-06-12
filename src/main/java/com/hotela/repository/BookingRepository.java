package com.hotela.repository;

import com.hotela.model.entity.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByCustomerId(UUID customerId);

    List<Booking> findByHotelId(UUID hotelId);

    List<Booking> findByRoomId(UUID roomId);

    Optional<Booking> findByIdAndCustomerId(UUID bookingId, UUID customerId);
}