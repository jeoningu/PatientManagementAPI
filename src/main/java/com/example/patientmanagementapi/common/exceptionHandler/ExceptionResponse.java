package com.example.patientmanagementapi.common.exceptionHandler;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ExceptionResponse {
    private LocalDateTime timestamp; // 에러가 발생한 시간
    private Integer status; // HttpStatus
    private String error; // 에러의 발생 원인
    private String exceptionClassName; //  exception class 명
    private final List<String> errMessage; // 에러메세지
}