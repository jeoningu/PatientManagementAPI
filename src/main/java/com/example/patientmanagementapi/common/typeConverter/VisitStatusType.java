package com.example.patientmanagementapi.common.typeConverter;

import com.example.patientmanagementapi.common.typeConverter.common.DbCodeCommonType;
import lombok.Getter;

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
}