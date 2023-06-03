package com.example.patientmanagementapi.common.typeConverter;

import com.example.patientmanagementapi.common.typeConverter.common.AbstractEnumAttributeConverter;

public class GenderTypeConverter extends AbstractEnumAttributeConverter<GenderType> {
    public static final String ENUM_NAME = "성별";

    GenderTypeConverter() {
        super(GenderType.class, ENUM_NAME);
    }
}
