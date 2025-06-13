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
        String validEmail = "user@example.com";
        boolean result = validationService.isValidEmail(validEmail);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForInvalidEmail() {
        String invalidEmail = "invalid-email";
        boolean result = validationService.isValidEmail(invalidEmail);
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseForNullEmail() {
        boolean result = validationService.isValidEmail(null);
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueForAdultAge() {
        LocalDate birthDate = LocalDate.now().minusYears(25);
        boolean result = validationService.isAdult(birthDate);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForMinorAge() {
        LocalDate birthDate = LocalDate.now().minusYears(15);
        boolean result = validationService.isAdult(birthDate);
        assertFalse(result);
    }
}
