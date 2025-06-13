package com.hotela.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HotelBusinessServiceTest {

    private HotelBusinessService hotelBusinessService;

    @BeforeEach
    void setUp() {
        hotelBusinessService = new HotelBusinessService();
    }

    @Test
    void shouldReturnTrueForValidStarRating() {
        BigDecimal validRating = BigDecimal.valueOf(4.5);
        boolean result = hotelBusinessService.isValidStarRating(validRating);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForInvalidHighStarRating() {
        BigDecimal invalidRating = BigDecimal.valueOf(6.0);
        boolean result = hotelBusinessService.isValidStarRating(invalidRating);
        assertFalse(result);
    }

    @Test
    void shouldReturnLuxoCategoryForHighRating() {
        BigDecimal highRating = BigDecimal.valueOf(4.8);
        String category = hotelBusinessService.getHotelCategory(highRating);
        assertEquals("Luxo", category);
    }

    @Test
    void shouldReturnEconomicoForLowRating() {
        BigDecimal lowRating = BigDecimal.valueOf(0.5);
        String category = hotelBusinessService.getHotelCategory(lowRating);
        assertEquals("Econômico", category);
    }

    @Test
    void shouldCalculateCorrectAverageRating() {
        BigDecimal currentRating = BigDecimal.valueOf(4.0);
        int totalReviews = 10;
        int newRating = 5;
        BigDecimal newAverage =
                hotelBusinessService.calculateAverageRating(currentRating, totalReviews, newRating);
        assertEquals(BigDecimal.valueOf(4.09), newAverage);
    }

    @Test
    void shouldApplyWeekendPriceIncrease() {
        BigDecimal basePrice = BigDecimal.valueOf(100.00);
        boolean isWeekend = true;
        BigDecimal weekendPrice = hotelBusinessService.calculateWeekendPrice(basePrice, isWeekend);
        assertEquals(0, BigDecimal.valueOf(120.00).compareTo(weekendPrice));
    }
}
