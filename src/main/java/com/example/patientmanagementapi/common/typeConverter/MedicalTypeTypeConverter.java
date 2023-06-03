package com.example.patientmanagementapi.common.typeConverter;

import com.example.patientmanagementapi.common.typeConverter.common.AbstractEnumAttributeConverter;

public class MedicalTypeTypeConverter extends AbstractEnumAttributeConverter<GenderType> {
    public static final String ENUM_NAME = "진료유형";

    MedicalTypeTypeConverter() {
        super(GenderType.class, ENUM_NAME);
    }
}
