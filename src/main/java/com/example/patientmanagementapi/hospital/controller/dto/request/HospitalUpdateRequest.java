package com.example.patientmanagementapi.hospital.controller.dto.request;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HospitalUpdateRequest {
    @NotBlank(message = "hospitalName은 필수값입니다.")
    @Size(max = 45, message = "hospitalName은 최대값이 45입니다.")
    private String hospitalName; // 병원명
    @NotBlank(message = "nursingHomeNo은 필수값입니다.")
    @Size(max = 20, message = "nursingHomeNo은 최대값이 20입니다.")
    private String nursingHomeNo; // 요양기관번호
    @NotBlank(message = "hospitalHeadName은 필수값입니다.")
    @Size(max = 10, message = "hospitalHeadName은 최대값이 10입니다.")
    private String hospitalHeadName; // 병원장명

}
