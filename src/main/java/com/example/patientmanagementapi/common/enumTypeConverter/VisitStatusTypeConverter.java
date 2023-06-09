package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.AbstractEnumAttributeConverter;

public class VisitStatusTypeConverter extends AbstractEnumAttributeConverter<VisitStatusType> {
    public static final String ENUM_NAME = "방문상태";

    VisitStatusTypeConverter() {
        super(VisitStatusType.class, ENUM_NAME);
    }
}
