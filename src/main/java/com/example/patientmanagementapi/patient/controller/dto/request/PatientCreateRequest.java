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
    @NotBlank(message = "genderType은 필수값입니다.")
    @Size(max = 10, message = "genderType은 최대값이 10입니다.")
    @Pattern(regexp= "M|F|H", message = "올바른 형식의 genderType을 입력해주세요(M|F|H)")
    private String genderType; // 병원장명
    @Size(max = 10, message = "birth은 최대값이 10입니다.")
    @Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "birth는 yyyy-MM-dd 형식으로 맞춰주세요")
    private String birth; // 생년월일
    @Size(max = 20, message = "phone은 최대값이 10입니다.")
    @Pattern(regexp = "\\d{3}-\\d{3,4}-\\d{4}", message = "phone은 000-0000-0000 형식으로 맞춰주세요")
    private String phone; // 휴대전화번호

    public Patient toEntity(Hospital hospital, String patientNo) {
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
