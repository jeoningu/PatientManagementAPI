package com.example.patientmanagementapi.patient.repository;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Modifying
    @Query("delete from Patient s where s.hospital.id = :hospitalId")
    int deleteAllByHospitalId(@Param("hospitalId") Long hospitalId);

    @Query("SELECT MAX(s.patientNo) FROM Patient s WHERE s.hospital = :hospital")
    String findMaxPatientNoByHospital(@Param("hospital")Hospital hospital);
}
