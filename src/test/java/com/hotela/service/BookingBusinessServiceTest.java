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
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = LocalDate.now().plusDays(7);
        boolean result = bookingBusinessService.canMakeReservation(checkin, checkout);
        assertTrue(result);
    }

    @Test
    void shouldNotAllowReservationForPastDates() {
        LocalDate checkin = LocalDate.now().minusDays(1);
        LocalDate checkout = LocalDate.now().plusDays(2);
        boolean result = bookingBusinessService.canMakeReservation(checkin, checkout);
        assertFalse(result);
    }

    @Test
    void shouldNotAllowReservationWhenCheckoutBeforeCheckin() {
        LocalDate checkin = LocalDate.now().plusDays(5);
        LocalDate checkout = LocalDate.now().plusDays(3);
        boolean result = bookingBusinessService.canMakeReservation(checkin, checkout);
        assertFalse(result);
    }

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        BigDecimal pricePerNight = BigDecimal.valueOf(100.00);
        LocalDate checkin = LocalDate.of(2024, 1, 1);
        LocalDate checkout = LocalDate.of(2024, 1, 4);
        BigDecimal total =
                bookingBusinessService.calculateTotalPrice(pricePerNight, checkin, checkout);
        assertEquals(BigDecimal.valueOf(300.00), total);
    }

    @Test
    void shouldNotAllowCancellationOfAlreadyCancelledBooking() {
        BookingStatus status = BookingStatus.CANCELLED_BY_CUSTOMER;
        LocalDate checkin = LocalDate.now().plusDays(5);
        boolean result = bookingBusinessService.canCancelReservation(status, checkin);
        assertFalse(result);
    }

    @Test
    void shouldNotAllowCancellationLessThan24HoursBeforeCheckin() {
        BookingStatus status = BookingStatus.CONFIRMED;
        LocalDate checkin = LocalDate.now().plusDays(1);
        boolean result = bookingBusinessService.canCancelReservation(status, checkin);
        assertFalse(result);
    }

    @Test
    void shouldValidateGuestCountWithinRoomCapacity() {
        int numberOfGuests = 3;
        int roomCapacity = 4;
        boolean result = bookingBusinessService.isValidGuestCount(numberOfGuests, roomCapacity);
        assertTrue(result);
    }

    @Test
    void shouldNotValidateGuestCountExceedingCapacity() {
        int numberOfGuests = 5;
        int roomCapacity = 4;
        boolean result = bookingBusinessService.isValidGuestCount(numberOfGuests, roomCapacity);
        assertFalse(result);
    }

    @Test
    void shouldCalculateFullRefundForEarlyCancellation() {
        BigDecimal totalAmount = BigDecimal.valueOf(500.00);
        LocalDate cancellationDate = LocalDate.now();
        LocalDate checkinDate = LocalDate.now().plusDays(10);
        BigDecimal refund =
                bookingBusinessService.calculateRefundAmount(
                        totalAmount, cancellationDate, checkinDate);
        assertEquals(totalAmount, refund);
    }

    @Test
    void shouldCalculatePartialRefundForMidTermCancellation() {
        BigDecimal totalAmount = BigDecimal.valueOf(500.00);
        LocalDate cancellationDate = LocalDate.now();
        LocalDate checkinDate = LocalDate.now().plusDays(5);
        BigDecimal refund =
                bookingBusinessService.calculateRefundAmount(
                        totalAmount, cancellationDate, checkinDate);
        assertEquals(0, BigDecimal.valueOf(250.00).compareTo(refund));
    }
}
