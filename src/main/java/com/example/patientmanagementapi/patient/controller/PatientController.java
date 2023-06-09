package com.example.patientmanagementapi.patient.controller;

import com.example.patientmanagementapi.hospital.service.HospitalService;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientCreateRequest;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientUpdateRequest;
import com.example.patientmanagementapi.patient.controller.dto.response.PatientResponse;
import com.example.patientmanagementapi.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/patient")
@RestController
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> create(@RequestBody @Valid PatientCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable Long id, @Valid @RequestBody PatientUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        patientService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
