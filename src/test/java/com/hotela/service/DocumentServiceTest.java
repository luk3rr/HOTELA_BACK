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
        String validCpf = "11144477735";
        boolean result = documentService.isValidDocument(validCpf, DocumentType.CPF);
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForInvalidCpf() {
        String invalidCpf = "12345678900";
        boolean result = documentService.isValidDocument(invalidCpf, DocumentType.CPF);
        assertFalse(result);
    }

    @Test
    void shouldCleanDocumentRemovingNonDigits() {
        String documentWithMask = "123.456.789-00";
        String result = documentService.cleanDocument(documentWithMask);
        assertEquals("12345678900", result);
    }

    @Test
    void shouldFormatCpfCorrectly() {
        String cleanCpf = "12345678900";
        String result = documentService.formatCpf(cleanCpf);
        assertEquals("123.456.789-00", result);
    }

    @Test
    void shouldFormatCnpjCorrectly() {
        String cleanCnpj = "12345678000195";
        String result = documentService.formatCnpj(cleanCnpj);
        assertEquals("12.345.678/0001-95", result);
    }
}
