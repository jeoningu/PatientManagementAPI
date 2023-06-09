package com.example.patientmanagementapi.patient.controller;

import com.example.patientmanagementapi.patient.controller.dto.request.PatientCreateRequest;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientUpdateRequest;
import com.example.patientmanagementapi.patient.controller.dto.response.PatientFindAllResponse;
import com.example.patientmanagementapi.patient.controller.dto.response.PatientResponse;
import com.example.patientmanagementapi.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Page<PatientFindAllResponse>> findAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String patientNo,
            @RequestParam(required = false) String birth) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(patientService.findAll(pageable, patientName, patientNo, birth));
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
