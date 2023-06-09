package com.example.patientmanagementapi.patient.controller.dto.response;

import com.example.patientmanagementapi.patient.domain.Patient;
import com.example.patientmanagementapi.visit.controller.dto.response.VisitResponse;
import com.example.patientmanagementapi.visit.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PatientResponse {

    private Long id; // 환자ID
    private Long hospitalId; // 병원ID
    private String patientName; // 환자명
    private String patientNo; // 환자등록번호
    private String genderType; // 성별코드
    private String birth; // 생년월일
    private String phone; // 휴대전화번호
    private List<VisitResponse> visits; // 방문

    public PatientResponse(Patient entity) {
        this.id = entity.getId();
        this.hospitalId = entity.getHospital().getId();
        this.patientName = entity.getPatientName();
        this.patientNo = entity.getPatientNo();
        this.genderType = entity.getGenderType().getCode();
        this.birth = entity.getBirth();
        this.phone = entity.getPhone();
        this.visits = entity.getVisits().stream().map(VisitResponse::new).collect(Collectors.toList());
    }

    public PatientResponse(Long id, Long hospitalId, String patientName, String patientNo, String genderType, String birth, String phone) {
        this.id = id;
        this.hospitalId = hospitalId;
        this.patientName = patientName;
        this.patientNo = patientNo;
        this.genderType = genderType;
        this.birth = birth;
        this.phone = phone;
    }
}
