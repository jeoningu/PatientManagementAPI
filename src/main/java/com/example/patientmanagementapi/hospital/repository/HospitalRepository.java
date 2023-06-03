package com.example.patientmanagementapi.hospital.repository;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
