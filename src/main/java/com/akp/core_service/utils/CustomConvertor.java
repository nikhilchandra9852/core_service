package com.akp.core_service.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.pgvector.PGvector;


@Converter(autoApply = true)
public class CustomConvertor implements AttributeConverter<float[], PGvector> {


    @Override
    public PGvector convertToDatabaseColumn(float[] attribute) {
        return attribute == null ? null : new PGvector(attribute);
    }

    @Override
    public float[] convertToEntityAttribute(PGvector dbData) {
        return dbData == null ? null : dbData.toArray();
    }
}
