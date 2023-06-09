package com.example.patientmanagementapi.hospital.service;

import com.example.patientmanagementapi.hospital.controller.dto.request.HospitalCreateRequest;
import com.example.patientmanagementapi.hospital.controller.dto.request.HospitalUpdateRequest;
import com.example.patientmanagementapi.hospital.controller.dto.response.HospitalResponse;
import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.hospital.repository.HospitalRepository;
import com.example.patientmanagementapi.patient.repository.PatientRepository;
import com.example.patientmanagementapi.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final VisitRepository visitRepository;

    @Transactional
    public HospitalResponse create(HospitalCreateRequest request) {
        Hospital savedHospital = hospitalRepository.save(request.toEntity());
        return new HospitalResponse(savedHospital);
    }

    @Transactional(readOnly = true)
    public HospitalResponse findById(Long id) {
        Hospital entity = hospitalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 병원이 없습니다. id = " + id));
        return new HospitalResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<HospitalResponse> findAll() {
        return hospitalRepository.findAll().stream()
                .map(HospitalResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public HospitalResponse update(Long id, HospitalUpdateRequest request) {
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 병원이 없습니다. id = " + id));
        hospital.update(request.getHospitalName(), request.getNursingHomeNo(), request.getHospitalHeadName());
        return new HospitalResponse(hospital);
    }

    @Transactional
    public void delete(Long id) {
        Hospital hospital = hospitalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 병원이 없습니다. id = " + id));

        // 삭제 쿼리 하나로 수행(N+1문제 해결)
        visitRepository.deleteAllByHospitalId(id);
        patientRepository.deleteAllByHospitalId(id);

        hospitalRepository.delete(hospital);
    }

}
