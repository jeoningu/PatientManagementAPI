package com.example.patientmanagementapi.visit.repository;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Hospital, Long> {
}
