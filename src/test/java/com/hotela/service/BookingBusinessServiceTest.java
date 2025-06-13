package com.hotela.service;

import static org.junit.jupiter.api.Assertions.*;

import com.hotela.model.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingBusinessServiceTest {

    private BookingBusinessService bookingBusinessService;

    @BeforeEach
    void setUp() {
        bookingBusinessService = new BookingBusinessService();
    }

    @Test
    void shouldAllowReservationForFutureDates() {
        // Given
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = LocalDate.now().plusDays(7);

        // When
        boolean result = bookingBusinessService.canMakeReservation(checkin, checkout);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldNotAllowReservationForPastDates() {
        // Given
        LocalDate checkin = LocalDate.now().minusDays(1);
        LocalDate checkout = LocalDate.now().plusDays(2);

        // When
        boolean result = bookingBusinessService.canMakeReservation(checkin, checkout);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldNotAllowReservationWhenCheckoutBeforeCheckin() {
        // Given
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = LocalDate.now().plusDays(3);

        // When
        boolean result = bookingBusinessService.canMakeReservation(checkin, checkout);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        // Given
        BigDecimal pricePerNight = BigDecimal.valueOf(100.00);
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 4); // 3 nights

        // When
        BigDecimal total =
                bookingBusinessService.calculateTotalPrice(pricePerNight, checkin, checkout);

        // Then
        assertEquals(BigDecimal.valueOf(300.00), total);
    }

    @Test
    void shouldNotAllowCancellationOfAlreadyCancelledBooking() {
        // Given
        BookingStatus status = BookingStatus.CANCELLED_BY_CUSTOMER;
        LocalDate checkin = LocalDate.now().plusDays(5);

        // When
        boolean result = bookingBusinessService.canCancelReservation(status, checkin);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldNotAllowCancellationLessThan24HoursBeforeCheckin() {
        // Given
        BookingStatus status = BookingStatus.CONFIRMED;
        LocalDate checkin = LocalDate.now().plusDays(1); // Exatamente 24h

        // When
        boolean result = bookingBusinessService.canCancelReservation(status, checkin);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldValidateGuestCountWithinRoomCapacity() {
        // Given
        int numberOfGuests = 3;
        int roomCapacity = 4;

        // When
        boolean result = bookingBusinessService.isValidGuestCount(numberOfGuests, roomCapacity);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldNotValidateGuestCountExceedingCapacity() {
        // Given
        int numberOfGuests = 5;
        int roomCapacity = 4;

        // When
        boolean result = bookingBusinessService.isValidGuestCount(numberOfGuests, roomCapacity);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldCalculateFullRefundForEarlyCancellation() {
        // Given
        BigDecimal totalAmount = BigDecimal.valueOf(500.00);
        LocalDate cancellationDate = LocalDate.now();
        LocalDate checkinDate = LocalDate.now().plusDays(10); // 10 dias de antecedência

        // When
        BigDecimal refund =
                bookingBusinessService.calculateRefundAmount(
                        totalAmount, cancellationDate, checkinDate);

        // Then
        assertEquals(totalAmount, refund);
    }

    @Test
    void shouldCalculatePartialRefundForMidTermCancellation() {
        // Given
        BigDecimal totalAmount = BigDecimal.valueOf(500.00);
        LocalDate cancellationDate = LocalDate.now();
        LocalDate checkinDate = LocalDate.now().plusDays(5); // 5 dias de antecedência

        // When
        BigDecimal refund =
                bookingBusinessService.calculateRefundAmount(
                        totalAmount, cancellationDate, checkinDate);

        // Then
        assertEquals(0, BigDecimal.valueOf(250.00).compareTo(refund)); // 50% do valor
    }
}
