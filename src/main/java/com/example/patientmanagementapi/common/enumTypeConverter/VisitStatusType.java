package com.example.patientmanagementapi.common.enumTypeConverter;

import com.example.patientmanagementapi.common.enumTypeConverter.common.DbCodeCommonType;
import lombok.Getter;

import java.util.Arrays;

/**
 * 방문상태, 환자방문의 상태(방문증, 종료, 취소)
 */
@Getter
public enum VisitStatusType implements DbCodeCommonType {
    VISIT("1", "방문증"),
    END("2", "종료"),
    CANCEL("3", "취소");

    private String code;
    private String codeName;

    VisitStatusType(String code, String codeName) {
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

    public static VisitStatusType valueOfCode(String code) {
        return Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("enum=[%s]에 code=[%s]가 존재하지 않습니다.",
                        "VisitStatusType", code)));
    }
}