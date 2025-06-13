package com.hotela.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

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
        LocalDate saturday = LocalDate.of(2024, 1, 6);
        boolean result = dateService.isWeekend(saturday);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForWeekdayDate() {
        LocalDate monday = LocalDate.of(2024, 1, 8);
        boolean result = dateService.isWeekend(monday);
        assertFalse(result);
    }

    @Test
    void shouldCalculateDaysBetweenDatesCorrectly() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        long result = dateService.daysBetween(startDate, endDate);
        assertEquals(9, result);
    }

    @Test
    void shouldFormatDateCorrectly() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        String result = dateService.formatDate(date);
        assertEquals("25/12/2024", result);
    }

    @Test
    void shouldReturnTrueForValidDateRange() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 10);
        boolean result = dateService.isValidDateRange(startDate, endDate);
        assertTrue(result);
    }
}
