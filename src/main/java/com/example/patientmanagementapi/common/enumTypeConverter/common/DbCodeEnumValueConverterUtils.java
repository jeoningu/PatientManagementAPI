package com.example.patientmanagementapi.common.enumTypeConverter.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

/**
 * DB Code와 Enum간 상호 변환 유틸리티 클래스
 * 공통적 Enum{@Method ofCode} 메서드 통합
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DbCodeEnumValueConverterUtils {

    /**
     * DB Code -> Enum 변환
     */
    public static <T extends Enum<T> & DbCodeCommonType> T ofDbCode(Class<T> enumClass, String dbCode) {

        if (!StringUtils.hasText(dbCode)) {
            return null;
        }

        return EnumSet.allOf(enumClass).stream()
                .filter(e -> e.getCode().equals(dbCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("enum=[%s]에 legacyCode=[%s]가 존재하지 않습니다.",
                        enumClass.getName(), dbCode)));
    }

    /**
     * Enum -> DB code 변환
     *
     */
    public static <T extends Enum<T> & DbCodeCommonType> String toDbCode(T enumValue) {
        return enumValue.getCode();
    }
}
