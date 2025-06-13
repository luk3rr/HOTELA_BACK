package com.hotela.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerBusinessServiceTest {

    private CustomerBusinessService customerBusinessService;

    @BeforeEach
    void setUp() {
        customerBusinessService = new CustomerBusinessService();
    }

    @Test
    void shouldAllowReservationForAdultCustomer() {
        // Given
        LocalDate adultBirthDate = LocalDate.now().minusYears(25);

        // When
        boolean result = customerBusinessService.canMakeReservation(adultBirthDate);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldNotAllowReservationForMinorCustomer() {
        // Given
        LocalDate minorBirthDate = LocalDate.now().minusYears(16);

        // When
        boolean result = customerBusinessService.canMakeReservation(minorBirthDate);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldReturnVipForFrequentLongTermCustomer() {
        // Given
        int totalBookings = 8;
        LocalDate firstBooking = LocalDate.now().minusYears(3);

        // When
        String customerType = customerBusinessService.getCustomerType(totalBookings, firstBooking);

        // Then
        assertEquals("Cliente VIP", customerType);
    }

    @Test
    void shouldReturnNewClientForFirstTime() {
        // Given
        int totalBookings = 0;

        // When
        String customerType = customerBusinessService.getCustomerType(totalBookings, null);

        // Then
        assertEquals("Novo Cliente", customerType);
    }

    @Test
    void shouldNotBeEligibleForDiscountWithRecentCancellation() {
        // Given
        int totalBookings = 3;
        boolean hasRecentCancellation = true;

        // When
        boolean eligible = customerBusinessService.isEligibleForDiscount(totalBookings, hasRecentCancellation);

        // Then
        assertFalse(eligible);
    }

    @Test
    void shouldCalculateCorrectLoyaltyPointsWithBonus() {
        // Given
        int totalBookings = 12; // Mais de 10 reservas
        int nightsStayed = 20;

        // When
        int points = customerBusinessService.calculateLoyaltyPoints(totalBookings, nightsStayed);

        // Then
        assertEquals(300, points); // 20 * 10 * 1.5 = 300
    }
}