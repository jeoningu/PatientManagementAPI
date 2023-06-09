package com.example.patientmanagementapi.visit.controller.dto.response;

import com.example.patientmanagementapi.visit.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VisitResponse {

    private Long id; // 환자방문ID
    private Long hospitalId; // 병원ID
    private Long patientId; // 환자ID
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime receptionDate; // 접수일시
    private String visitStatusType; // 방문상태코드
    public VisitResponse(Visit entity) {
        this.id = entity.getId();
        this.hospitalId = entity.getHospital().getId();
        this.patientId = entity.getPatient().getId();
        this.receptionDate = entity.getReceptionDate();
        this.visitStatusType = entity.getVisitStatusType().getCode();
    }
}
