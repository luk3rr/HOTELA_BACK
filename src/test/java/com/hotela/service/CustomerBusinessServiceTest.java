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
        LocalDate adultBirthDate = LocalDate.now().minusYears(25);
        boolean result = customerBusinessService.canMakeReservation(adultBirthDate);
        assertTrue(result);
    }

    @Test
    void shouldNotAllowReservationForMinorCustomer() {
        LocalDate minorBirthDate = LocalDate.now().minusYears(16);
        boolean result = customerBusinessService.canMakeReservation(minorBirthDate);
        assertFalse(result);
    }

    @Test
    void shouldReturnVipForFrequentLongTermCustomer() {
        int totalBookings = 8;
        LocalDate firstBooking = LocalDate.now().minusYears(3);
        String customerType = customerBusinessService.getCustomerType(totalBookings, firstBooking);
        assertEquals("Cliente VIP", customerType);
    }

    @Test
    void shouldReturnNewClientForFirstTime() {
        int totalBookings = 0;
        String customerType = customerBusinessService.getCustomerType(totalBookings, null);
        assertEquals("Novo Cliente", customerType);
    }

    @Test
    void shouldNotBeEligibleForDiscountWithRecentCancellation() {
        int totalBookings = 3;
        boolean hasRecentCancellation = true;
        boolean eligible = customerBusinessService.isEligibleForDiscount(totalBookings, hasRecentCancellation);
        assertFalse(eligible);
    }

    @Test
    void shouldCalculateCorrectLoyaltyPointsWithBonus() {
        int totalBookings = 12;
        int nightsStayed = 20;
        int points = customerBusinessService.calculateLoyaltyPoints(totalBookings, nightsStayed);
        assertEquals(300, points);
    }
}
