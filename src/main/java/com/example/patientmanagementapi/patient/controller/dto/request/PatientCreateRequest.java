package com.example.patientmanagementapi.patient.controller.dto.request;

import com.example.patientmanagementapi.common.enumTypeConverter.GenderType;
import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.patient.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PatientCreateRequest {
    @NotBlank(message = "hospitalId은 필수값입니다.")
    private String hospitalId; // 병원ID
    @NotBlank(message = "patientName은 필수값입니다.")
    @Size(max = 45, message = "patientName은 최대값이 45입니다.")
    private String patientName; // 환자명
    @NotBlank(message = "patientNo은 필수값입니다.")
    @Size(max = 13, message = "patientNo은 최대값이 13입니다.")
    private String patientNo; // 환자등록번호
    @NotBlank(message = "genderType은 필수값입니다.")
    @Size(max = 10, message = "genderType은 최대값이 10입니다.")
    @Pattern(regexp= "M|F|H", message = "올바른 형식의 genderType을 입력해주세요(M|F|H)")
    private String genderType; // 병원장명
    @Size(max = 10, message = "birth은 최대값이 10입니다.")
    private String birth; // 생년월일
    @Size(max = 20, message = "phone은 최대값이 10입니다.")
    private String phone; // 휴대전화번호

    public Patient toEntity(Hospital hospital) {
        return Patient.builder()
                .hospital(hospital)
                .patientName(patientName)
                .patientNo(patientNo)
                .genderType(GenderType.valueOfCode(genderType))
                .birth(birth)
                .phone(phone)
                .build();
    }
}
