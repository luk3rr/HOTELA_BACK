package com.hotela.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    private ValidationService validationService;

    @BeforeEach
    void setUp() {
        validationService = new ValidationService();
    }

    @Test
    void shouldReturnTrueForValidEmail() {
        // Given
        String validEmail = "user@example.com";

        // When
        boolean result = validationService.isValidEmail(validEmail);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForInvalidEmail() {
        // Given
        String invalidEmail = "invalid-email";

        // When
        boolean result = validationService.isValidEmail(invalidEmail);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForNullEmail() {
        // When
        boolean result = validationService.isValidEmail(null);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueForAdultAge() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(25);

        // When
        boolean result = validationService.isAdult(birthDate);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForMinorAge() {
        // Given
        LocalDate birthDate = LocalDate.now().minusYears(15);

        // When
        boolean result = validationService.isAdult(birthDate);

        // Then
        assertFalse(result);
    }
}