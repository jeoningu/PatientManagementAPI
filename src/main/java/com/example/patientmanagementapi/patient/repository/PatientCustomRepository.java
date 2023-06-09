package com.example.patientmanagementapi.patient.repository;

import com.example.patientmanagementapi.patient.controller.dto.response.PatientFindAllResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientCustomRepository {
    Page<PatientFindAllResponse> findBySearchOption(Pageable pageable, String patientName, String patientNo, String birth);

}
