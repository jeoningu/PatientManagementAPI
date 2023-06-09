package com.example.patientmanagementapi.patient.controller.dto.response;

import com.example.patientmanagementapi.patient.domain.Patient;
import com.example.patientmanagementapi.visit.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
@AllArgsConstructor
public class PatientFindAllResponse {

    private Long id; // 환자ID
    private Long hospitalId; // 병원ID
    private String patientName; // 환자명
    private String patientNo; // 환자등록번호
    private String genderType; // 성별코드
    private String birth; // 생년월일
    private String phone; // 휴대전화번호
    private LocalDateTime recentVisitDate; // 최근방문일

    public PatientFindAllResponse(Patient entity) {
        this.id = entity.getId();
        this.hospitalId = entity.getHospital().getId();
        this.patientName = entity.getPatientName();
        this.patientNo = entity.getPatientNo();
        this.genderType = entity.getGenderType().getCode();
        this.birth = entity.getBirth();
        this.phone = entity.getPhone();
        this.recentVisitDate = findRecentVisitDate(entity); // 최근방문일 설정
    }

    private LocalDateTime findRecentVisitDate(Patient entity) {
        if (entity.getVisits() != null && !entity.getVisits().isEmpty()) {
            return entity.getVisits().stream()
                    .map(Visit::getReceptionDate)
                    .max(Comparator.naturalOrder())
                    .orElse(null);
        }
        return null;
    }
}
