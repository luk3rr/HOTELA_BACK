package com.hotela.domain.converter;

import com.hotela.domain.vo.Cnpj;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @details Converte a classe Cnpj (Value Object) para String (apenas números) para persistência no
 *     banco e vice-versa.
 */
@Converter
public class CnpjConverter implements AttributeConverter<Cnpj, String> {

    /**
     * @details Converte o Value Object Cnpj para uma String (apenas números) a ser armazenada no
     *     banco.
     * @param attribute O objeto Cnpj. Pode ser null.
     * @return A representação String do CNPJ (apenas números), ou null se o atributo for null.
     */
    @Override
    public String convertToDatabaseColumn(Cnpj attribute) {
        if (attribute == null) {
            return null;
        }
        // Assumindo que Cnpj.getValue() retorna apenas os números
        return attribute.getValue();
    }

    /**
     * @details Converte a String (apenas números) do banco de dados de volta para um objeto Cnpj.
     * @param dbData A String (apenas números) vinda do banco. Pode ser null.
     * @return Um objeto Cnpj, ou null se os dados do banco forem null ou vazios.
     * @throws IllegalArgumentException se a string do banco não formar um CNPJ válido (a validação
     *     é feita no construtor de Cnpj).
     */
    @Override
    public Cnpj convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        return new Cnpj(dbData);
    }
}
