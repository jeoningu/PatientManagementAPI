package com.example.patientmanagementapi.common.typeConverter;

import com.example.patientmanagementapi.common.typeConverter.common.DbCodeCommonType;
import lombok.Getter;

/**
 * 성별, 성별을 표시
 */
@Getter
public enum GenderType implements DbCodeCommonType {
    MALE("M", "남자"),
    FEMALE("F", "여자"),
    HUMAN("H", "모름");

    private String code;
    private String codeName;

    GenderType(String code, String codeName) {
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
