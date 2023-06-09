package com.example.patientmanagementapi.visit.controller.dto.request;

import com.example.patientmanagementapi.common.enumTypeConverter.VisitStatusType;
import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.patient.domain.Patient;
import com.example.patientmanagementapi.visit.domain.Visit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class VisitCreateRequest {
    @NotBlank(message = "hospitalId 필수값입니다.")
    private String hospitalId; // 병원ID
    @NotBlank(message = "patientId은 필수값입니다.")
    private String patientId; // 환자ID
    @NotNull(message = "receptionDate은 필수값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime receptionDate; // 접수일시
    @NotBlank(message = "visitStatusType은 필수값입니다.")
    @Size(max = 10, message = "visitStatusType은 최대값이 10입니다.")
    @Pattern(regexp= "1|2|3", message = "올바른 형식의 visitStatusType을 입력해주세요(1|2|3)")
    private String visitStatusType; // 방문상태코드

    public Visit toEntity(Hospital hospital, Patient patient) {
        return Visit.builder()
                .hospital(hospital)
                .patient(patient)
                .receptionDate(receptionDate)
                .visitStatusType(VisitStatusType.valueOfCode(visitStatusType))
                .build();
    }
}
