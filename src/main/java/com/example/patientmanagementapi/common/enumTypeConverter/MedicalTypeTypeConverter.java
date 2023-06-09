package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.AbstractEnumAttributeConverter;

public class MedicalTypeTypeConverter extends AbstractEnumAttributeConverter<MedicalTypeType> {
    public static final String ENUM_NAME = "진료유형";

    MedicalTypeTypeConverter() {
        super(MedicalTypeType.class, ENUM_NAME);
    }
}
