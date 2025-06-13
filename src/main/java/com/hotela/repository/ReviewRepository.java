package com.hotela.repository;

import com.hotela.model.entity.Review;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByHotelId(UUID hotelId);

    List<Review> findByCustomerId(UUID customerId);

    Optional<Review> findByBookingId(UUID bookingId);
}