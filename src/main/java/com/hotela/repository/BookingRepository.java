package com.hotela.repository;

import com.hotela.model.entity.Booking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Booking entity.
 * Provides methods to perform CRUD operations and custom queries on Booking data.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    /**
     * Finds all bookings for a specific customer.
     * @param customerId the UUID of the customer
     * @return list of bookings for the customer
     */
    List<Booking> findByCustomerId(UUID customerId);

    /**
     * Finds all bookings for a specific hotel.
     * @param hotelId the UUID of the hotel
     * @return list of bookings for the hotel
     */
    List<Booking> findByHotelId(UUID hotelId);

    /**
     * Finds all bookings for a specific room.
     * @param roomId the UUID of the room
     * @return list of bookings for the room
     */
    List<Booking> findByRoomId(UUID roomId);

    /**
     * Finds a booking by its ID and customer ID.
     * @param bookingId the UUID of the booking
     * @param customerId the UUID of the customer
     * @return an Optional containing the booking if found, or empty otherwise
     */
    Optional<Booking> findByIdAndCustomerId(UUID bookingId, UUID customerId);
}