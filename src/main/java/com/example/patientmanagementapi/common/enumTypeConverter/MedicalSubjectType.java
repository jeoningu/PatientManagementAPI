package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.DbCodeCommonType;
import lombok.Getter;

import java.util.Arrays;

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

    public static MedicalSubjectType valueOfCode(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("enum=[%s]에 code=[%s]가 존재하지 않습니다.",
                        "MedicalSubjectType", code)));
    }
}