package com.example.patientmanagementapi.patient.controller.dto.response;

import com.example.patientmanagementapi.common.enumTypeConverter.GenderType;
import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.patient.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

    public PatientResponse(Patient entity) {
        this.id = entity.getId();
        this.hospitalId = entity.getHospital().getId();
        this.patientName = entity.getPatientName();
        this.patientNo = entity.getPatientNo();
        this.genderType = entity.getGenderType().getCode();
        this.birth = entity.getBirth();
        this.phone = entity.getPhone();
    }
}
