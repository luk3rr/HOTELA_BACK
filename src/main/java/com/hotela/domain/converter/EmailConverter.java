package com.hotela.domain.converter;

import com.hotela.domain.vo.Email;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @details Converte a classe Email (Value Object) para String para persistência no banco e
 *     vice-versa.
 */
@Converter
public class EmailConverter implements AttributeConverter<Email, String> {

    /**
     * @details Converte o Value Object Email para uma String a ser armazenada no banco.
     * @param attribute O objeto Email. Pode ser null.
     * @return A representação String do email, ou null se o atributo Email for null.
     */
    @Override
    public String convertToDatabaseColumn(Email attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    /**
     * @details Converte a String do banco de dados de volta para um objeto Email.
     * @param dbData A String vinda do banco. Pode ser null.
     * @return Um objeto Email, ou null se os dados do banco forem null.
     * @throws IllegalArgumentException se a string do banco não for um email válido (a validação é
     *     feita no construtor de Email).
     */
    @Override
    public Email convertToEntityAttribute(String dbData) {
        if (dbData == null
                || dbData.trim().isEmpty()) { // Adicionada verificação para string vazia também
            return null;
        }

        return new Email(dbData);
    }
}
