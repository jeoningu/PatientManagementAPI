package com.example.patientmanagementapi.visit.repository;

import com.example.patientmanagementapi.visit.domain.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Modifying
    @Query("delete from Visit s where s.hospital.id = :hospitalId")
    int deleteAllByHospitalId(@Param("hospitalId") Long hospitalId);

    @Modifying
    @Query("delete from Visit s where s.patient.id = :patientId")
    int deleteAllByPatientId(@Param("patientId") Long patientId);
}
