package com.example.patientmanagementapi.visit.controller;

import com.example.patientmanagementapi.visit.service.VisitService;
import com.example.patientmanagementapi.visit.controller.dto.request.VisitCreateRequest;
import com.example.patientmanagementapi.visit.controller.dto.request.VisitUpdateRequest;
import com.example.patientmanagementapi.visit.controller.dto.response.VisitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/visit")
@RestController
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitResponse> create(@RequestBody @Valid VisitCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(visitService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(visitService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<VisitResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(visitService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitResponse> update(@PathVariable Long id, @Valid @RequestBody VisitUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(visitService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        visitService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
