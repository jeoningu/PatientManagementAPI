package com.example.patientmanagementapi.common.typeConverter.common;

import javax.persistence.AttributeConverter;

/**
 * 공통코드 Enum Converter들에서 상속받아서 사용
 * @param <E>
 */
public class AbstractEnumAttributeConverter<E extends Enum<E> & DbCodeCommonType> implements AttributeConverter<E, String> {

    /**
     * 대상 Enum 클랙스 객체{@link Class}
     */
    private Class<E> targetEnumClass;

    /**
     * {@NotNull Enum} Enum에 대한 오류 메시지 출력에 사용
     */
    private String enumName;

    public AbstractEnumAttributeConverter(Class<E> element, String enumName) {
        this.targetEnumClass = element;
        this.enumName = enumName;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        // 모든 Enum 타입 컬럼엔 null, "" 이 들어갈 수 없음
        if (attribute == null) {
            throw new IllegalArgumentException(String.format("[%s]는 NULL로 저장될 수 없습니다.", enumName));
        }
        return DbCodeEnumValueConverterUtils.toDbCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return DbCodeEnumValueConverterUtils.ofDbCode(targetEnumClass, dbData);
    }
}
