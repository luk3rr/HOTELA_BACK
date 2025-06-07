package com.hotela.domain.converter;

import com.hotela.domain.vo.Telephone;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TelephoneConverter implements AttributeConverter<Telephone, String> {

    @Override
    public String convertToDatabaseColumn(Telephone attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public Telephone convertToEntityAttribute(String dbData) {
        // A validação e trim já ocorrem no construtor de Telephone
        return dbData == null || dbData.trim().isEmpty() ? null : new Telephone(dbData);
    }
}
