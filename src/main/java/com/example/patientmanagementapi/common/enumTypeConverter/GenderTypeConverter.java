package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.AbstractEnumAttributeConverter;

public class GenderTypeConverter extends AbstractEnumAttributeConverter<GenderType> {
    public static final String ENUM_NAME = "성별";

    GenderTypeConverter() {
        super(GenderType.class, ENUM_NAME);
    }
}
