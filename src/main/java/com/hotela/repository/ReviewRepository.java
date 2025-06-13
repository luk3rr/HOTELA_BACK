package com.hotela.repository;

import com.hotela.model.entity.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Review entities from the database.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    /**
     * Finds all reviews for a given hotel by its ID.
     *
     * @param hotelId the UUID of the hotel
     * @return a list of reviews for the specified hotel
     */
    List<Review> findByHotelId(UUID hotelId);

    /**
     * Finds all reviews made by a specific customer.
     *
     * @param customerId the UUID of the customer
     * @return a list of reviews made by the specified customer
     */
    List<Review> findByCustomerId(UUID customerId);

    /**
     * Finds a review associated with a specific booking.
     *
     * @param bookingId the UUID of the booking
     * @return an Optional containing the review if found, or empty otherwise
     */
    Optional<Review> findByBookingId(UUID bookingId);
}