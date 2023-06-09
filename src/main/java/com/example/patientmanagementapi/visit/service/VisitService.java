package com.example.patientmanagementapi.visit.service;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.hospital.repository.HospitalRepository;
import com.example.patientmanagementapi.patient.domain.Patient;
import com.example.patientmanagementapi.patient.repository.PatientRepository;
import com.example.patientmanagementapi.visit.controller.dto.request.VisitCreateRequest;
import com.example.patientmanagementapi.visit.controller.dto.request.VisitUpdateRequest;
import com.example.patientmanagementapi.visit.controller.dto.response.VisitResponse;
import com.example.patientmanagementapi.visit.domain.Visit;
import com.example.patientmanagementapi.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;

    @Transactional
    public VisitResponse create(VisitCreateRequest request) {
        Hospital hospital = hospitalRepository.findById(Long.parseLong(request.getHospitalId())).orElseThrow(() -> new IllegalArgumentException("해당 병원이 없습니다. id = " + request.getHospitalId()));
        Patient patient = patientRepository.findById(Long.parseLong(request.getPatientId())).orElseThrow(() -> new IllegalArgumentException("해당 환자가 없습니다. id = " + request.getPatientId()));
        Visit savedVisit = visitRepository.save(request.toEntity(hospital, patient));
        return new VisitResponse(savedVisit);
    }

    @Transactional(readOnly = true)
    public VisitResponse findById(Long id) {
        Visit entity = visitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 환자방문 데이터가 없습니다. id = " + id));
        return new VisitResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> findAll() {
        return visitRepository.findAll().stream()
                .map(VisitResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public VisitResponse update(Long id, VisitUpdateRequest request) {
        Hospital hospital = hospitalRepository.findById(Long.parseLong(request.getHospitalId())).orElseThrow(() -> new IllegalArgumentException("해당 병원이 없습니다. id = " + request.getHospitalId()));
        Patient patient = patientRepository.findById(Long.parseLong(request.getPatientId())).orElseThrow(() -> new IllegalArgumentException("해당 환자가 없습니다. id = " + request.getPatientId()));
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 방문 데이터가 없습니다. id = " + id));
        visit.update(hospital, patient, request.getReceptionDate(), request.getVisitStatusType());
        return new VisitResponse(visit);
    }

    @Transactional
    public void delete(Long id) {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 환자방문 데이터가 없습니다. id = " + id));
        visitRepository.delete(visit);
    }

}
