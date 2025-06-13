package com.hotela.service;

import com.hotela.domain.vo.Cnpj;
import com.hotela.domain.vo.Cpf;
import com.hotela.model.enums.DocumentType;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    public boolean isValidDocument(String documentValue, DocumentType documentType) {
        if (documentValue == null || documentValue.trim().isEmpty() || documentType == null) {
            return false;
        }

        try {
            switch (documentType) {
                case CPF:
                    new Cpf(documentValue);
                    return true;
                case CNPJ:
                    new Cnpj(documentValue);
                    return true;
                default:
                    return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String cleanDocument(String documentValue) {
        if (documentValue == null) {
            return null;
        }
        return documentValue.replaceAll("[^0-9]", "");
    }

    public String formatCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9, 11));
    }

    public String formatCnpj(String cnpj) {
        if (cnpj == null || cnpj.length() != 14) {
            return cnpj;
        }
        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, 2),
                cnpj.substring(2, 5),
                cnpj.substring(5, 8),
                cnpj.substring(8, 12),
                cnpj.substring(12, 14));
    }
}