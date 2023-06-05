package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.AbstractEnumAttributeConverter;

public class MedicalTypeTypeConverter extends AbstractEnumAttributeConverter<GenderType> {
    public static final String ENUM_NAME = "진료유형";

    MedicalTypeTypeConverter() {
        super(GenderType.class, ENUM_NAME);
    }
}
