package com.hotela.domain.converter;

import com.hotela.domain.vo.Cpf;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @details Converte a classe Cpf (Value Object) para String (apenas números) para persistência no
 *     banco e vice-versa.
 */
@Converter
public class CpfConverter implements AttributeConverter<Cpf, String> {

    /**
     * @details Converte o Value Object Cpf para uma String (apenas números) a ser armazenada no
     *     banco.
     * @param attribute O objeto Cpf. Pode ser null.
     * @return A representação String do CPF (apenas números), ou null se o atributo for null.
     */
    @Override
    public String convertToDatabaseColumn(Cpf attribute) {
        if (attribute == null) {
            return null;
        }
        // Assumindo que Cpf.getValue() retorna apenas os números
        return attribute.getValue();
    }

    /**
     * @details Converte a String (apenas números) do banco de dados de volta para um objeto Cpf.
     * @param dbData A String (apenas números) vinda do banco. Pode ser null.
     * @return Um objeto Cpf, ou null se os dados do banco forem null ou vazios.
     * @throws IllegalArgumentException se a string do banco não formar um CPF válido (a validação é
     *     feita no construtor de Cpf).
     */
    @Override
    public Cpf convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        return new Cpf(dbData); // O construtor de Cpf já espera e valida
    }
}
