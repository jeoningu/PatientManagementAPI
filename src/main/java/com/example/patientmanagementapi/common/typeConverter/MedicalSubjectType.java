package com.example.patientmanagementapi.common.typeConverter;

import com.example.patientmanagementapi.common.typeConverter.common.DbCodeCommonType;
import lombok.Getter;

/**
 * 진료과목, 진료과목(내과, 안과 등)
 */
@Getter
public enum MedicalSubjectType implements DbCodeCommonType {
    INTERNAL("01", "내과"),
    SURGERY("02", "안과");

    private String code;
    private String codeName;

    MedicalSubjectType(String code, String codeName) {
        this.codeName = codeName;
        this.code = code;
    }

    @Override
    public String getCodeName() {
        return this.codeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}