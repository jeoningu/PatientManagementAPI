package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.DbCodeCommonType;
import lombok.Getter;

/**
 * 진료유형, 진료의 유형(약처방, 검사 등)
 */
@Getter
public enum MedicalTypeType implements DbCodeCommonType {
    DRUG("D", "약처방"),
    TEST("T", "검사");

    private String code;
    private String codeName;

    MedicalTypeType(String code, String codeName) {
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