package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.DbCodeCommonType;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

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

    public static GenderType valueOfCode(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("enum=[%s]에 code=[%s]가 존재하지 않습니다.",
                        "GenderType", code)));
    }
}
