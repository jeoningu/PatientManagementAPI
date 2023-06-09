package com.example.patientmanagementapi.patient.service;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.hospital.repository.HospitalRepository;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientCreateRequest;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientUpdateRequest;
import com.example.patientmanagementapi.patient.controller.dto.response.PatientResponse;
import com.example.patientmanagementapi.patient.domain.Patient;
import com.example.patientmanagementapi.patient.repository.PatientRepository;
import com.example.patientmanagementapi.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final VisitRepository visitRepository;

    @Transactional
    public PatientResponse create(PatientCreateRequest request) {
        Hospital hospital = hospitalRepository.findById(Long.parseLong(request.getHospitalId())).orElseThrow(() -> new IllegalArgumentException("해당 병원이 없습니다. id = " + request.getHospitalId()));

        String patientNo = generateUniquePatientNo(hospital);

        Patient savedPatient = patientRepository.save(request.toEntity(hospital, patientNo));
        return new PatientResponse(savedPatient);
    }

    /**
     * 환자등록번호는 병원별로 중복되지 않도록 서버에서 생성
     * @param hospital
     * @return
     */
    private synchronized String generateUniquePatientNo(Hospital hospital) {
        String maxPatientNo = patientRepository.findMaxPatientNoByHospital(hospital);
        int nextSequenceNumber = maxPatientNo != null ? Integer.parseInt(maxPatientNo) + 1 : 1;

        return String.valueOf(nextSequenceNumber);
    }

    @Transactional(readOnly = true)
    public PatientResponse findById(Long id) {
        Patient entity = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 환자가 없습니다. id = " + id));
        return new PatientResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> findAll() {
        return patientRepository.findAll().stream()
                .map(PatientResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PatientResponse update(Long id, PatientUpdateRequest request) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 환자가 없습니다. id = " + id));
        Hospital hospital = hospitalRepository.findById(Long.parseLong(request.getHospitalId())).orElseThrow(() -> new IllegalArgumentException("해당 병원이 없습니다. id = " + request.getHospitalId()));
        patient.update(hospital, request.getPatientName(), request.getGenderType(), request.getBirth(), request.getPhone());
        return new PatientResponse(patient);
    }

    @Transactional
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 환자가 없습니다. id = " + id));

        // 삭제 쿼리 하나로 수행(N+1문제 해결)
        visitRepository.deleteAllByPatientId(id);

        patientRepository.delete(patient);
    }

}
