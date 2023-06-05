package com.example.patientmanagementapi.common.enumTypeConverter.common;

/**
 * 공통코드 Enum 들에서 사용할 공통 메서드를 추상화
 */
public interface DbCodeCommonType {
    /**
     * 공통 코드 반환
     * @return String 공통코드값
     */
    String getCode();
    String getCodeName();
}
