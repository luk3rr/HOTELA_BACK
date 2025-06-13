package com.hotela.service;

import com.hotela.model.enums.DocumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        documentService = new DocumentService();
    }

    @Test
    void shouldReturnTrueForValidCpf() {
        // Given
        String validCpf = "11144477735";

        // When
        boolean result = documentService.isValidDocument(validCpf, DocumentType.CPF);

        // Then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForInvalidCpf() {
        // Given
        String invalidCpf = "12345678900";

        // When
        boolean result = documentService.isValidDocument(invalidCpf, DocumentType.CPF);

        // Then
        assertFalse(result);
    }

    @Test
    void shouldCleanDocumentRemovingNonDigits() {
        // Given
        String documentWithMask = "123.456.789-00";

        // When
        String result = documentService.cleanDocument(documentWithMask);

        // Then
        assertEquals("12345678900", result);
    }

    @Test
    void shouldFormatCpfCorrectly() {
        // Given
        String cleanCpf = "12345678900";

        // When
        String result = documentService.formatCpf(cleanCpf);

        // Then
        assertEquals("123.456.789-00", result);
    }

    @Test
    void shouldFormatCnpjCorrectly() {
        // Given
        String cleanCnpj = "12345678000195";

        // When
        String result = documentService.formatCnpj(cleanCnpj);

        // Then
        assertEquals("12.345.678/0001-95", result);
    }
}