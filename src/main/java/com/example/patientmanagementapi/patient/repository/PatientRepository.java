package com.example.patientmanagementapi.patient.repository;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Hospital, Long> {
}
