package com.hotela.repository;

import com.hotela.model.entity.Payment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Payment entity.
 * Provides CRUD operations and custom queries for Payment.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    /**
     * Finds a Payment by its associated Booking ID.
     *
     * @param bookingId the UUID of the booking
     * @return an Optional containing the Payment if found, or empty otherwise
     */
    Optional<Payment> findByBookingId(UUID bookingId);

    /**
     * Finds a Payment by its external transaction ID.
     *
     * @param externalTransactionId the external transaction identifier
     * @return an Optional containing the Payment if found, or empty otherwise
     */
    Optional<Payment> findByExternalTransactionId(String externalTransactionId);
}