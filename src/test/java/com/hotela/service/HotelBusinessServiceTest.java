package com.hotela.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HotelBusinessServiceTest {

    private HotelBusinessService hotelBusinessService;

    @BeforeEach
    void setUp() {
        hotelBusinessService = new HotelBusinessService();
    }

    @Test
    void shouldReturnTrueForValidStarRating() {
        // Given
        BigDecimal validRating = BigDecimal.valueOf(4.5);

        // When
        boolean result = hotelBusinessService.isValidStarRating(validRating);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForInvalidHighStarRating() {
        // Given
        BigDecimal invalidRating = BigDecimal.valueOf(6.0);

        // When
        boolean result = hotelBusinessService.isValidStarRating(invalidRating);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldReturnLuxoCategoryForHighRating() {
        // Given
        BigDecimal highRating = BigDecimal.valueOf(4.8);

        // When
        String category = hotelBusinessService.getHotelCategory(highRating);

        // Then
        assertEquals("Luxo", category);
    }

    @Test
    void shouldReturnEconomicoForLowRating() {
        // Given
        BigDecimal lowRating = BigDecimal.valueOf(0.5);

        // When
        String category = hotelBusinessService.getHotelCategory(lowRating);

        // Then
        assertEquals("Econômico", category);
    }

    @Test
    void shouldCalculateCorrectAverageRating() {
        // Given
        BigDecimal currentRating = BigDecimal.valueOf(4.0);
        int totalReviews = 10;
        int newRating = 5;

        // When
        BigDecimal newAverage = hotelBusinessService.calculateAverageRating(currentRating, totalReviews, newRating);

        // Then
        assertEquals(BigDecimal.valueOf(4.09), newAverage);
    }

    @Test
    void shouldApplyWeekendPriceIncrease() {
        // Given
        BigDecimal basePrice = BigDecimal.valueOf(100.00);
        boolean isWeekend = true;

        // When
        BigDecimal weekendPrice = hotelBusinessService.calculateWeekendPrice(basePrice, isWeekend);

        // Then
        assertEquals(BigDecimal.valueOf(120.00), weekendPrice);
    }
}