package com.example.patientmanagementapi.hospital.controller;

import com.example.patientmanagementapi.hospital.controller.dto.request.HospitalCreateRequest;
import com.example.patientmanagementapi.hospital.controller.dto.request.HospitalUpdateRequest;
import com.example.patientmanagementapi.hospital.controller.dto.response.HospitalResponse;
import com.example.patientmanagementapi.hospital.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/hospital")
@RestController
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping
    public ResponseEntity<HospitalResponse> create(@RequestBody @Valid HospitalCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<HospitalResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponse> update(@PathVariable Long id, @Valid @RequestBody HospitalUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(hospitalService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        hospitalService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
