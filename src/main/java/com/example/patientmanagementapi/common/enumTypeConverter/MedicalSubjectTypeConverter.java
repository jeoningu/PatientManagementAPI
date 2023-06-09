package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.AbstractEnumAttributeConverter;

public class MedicalSubjectTypeConverter extends AbstractEnumAttributeConverter<MedicalSubjectType> {
    public static final String ENUM_NAME = "진료과목";

    MedicalSubjectTypeConverter() {
        super(MedicalSubjectType.class, ENUM_NAME);
    }
}
