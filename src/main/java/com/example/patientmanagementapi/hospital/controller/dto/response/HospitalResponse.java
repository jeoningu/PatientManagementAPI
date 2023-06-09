package com.example.patientmanagementapi.hospital.controller.dto.response;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HospitalResponse {

    private Long id; // 병원ID
    private String hospitalName; // 병원명
    private String nursingHomeNo; // 요양기관번호
    private String hospitalHeadName; // 병원장명

    public HospitalResponse(Hospital entity) {
        this.id = entity.getId();
        this.hospitalName = entity.getHospitalName();
        this.nursingHomeNo = entity.getNursingHomeNo();
        this.hospitalHeadName = entity.getHospitalHeadName();
    }
}
