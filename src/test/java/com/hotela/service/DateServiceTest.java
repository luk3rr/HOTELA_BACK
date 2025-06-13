package com.hotela.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateServiceTest {

    private DateService dateService;

    @BeforeEach
    void setUp() {
        dateService = new DateService();
    }

    @Test
    void shouldReturnTrueForWeekendDate() {
        // Given - Saturday
        LocalDate saturday = LocalDate.of(2024, 1, 6);

        // When
        boolean result = dateService.isWeekend(saturday);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForWeekdayDate() {
        // Given - Monday
        LocalDate monday = LocalDate.of(2024, 1, 8);

        // When
        boolean result = dateService.isWeekend(monday);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldCalculateDaysBetweenDatesCorrectly() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);

        // When
        long result = dateService.daysBetween(startDate, endDate);

        // Then
        assertEquals(9, result);
    }

    @Test
    void shouldFormatDateCorrectly() {
        // Given
        LocalDate date = LocalDate.of(2024, 12, 25);

        // When
        String result = dateService.formatDate(date);

        // Then
        assertEquals("25/12/2024", result);
    }

    @Test
    void shouldReturnTrueForValidDateRange() {
        // Given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);

        // When
        boolean result = dateService.isValidDateRange(startDate, endDate);

        // Then
        assertTrue(result);
    }
}