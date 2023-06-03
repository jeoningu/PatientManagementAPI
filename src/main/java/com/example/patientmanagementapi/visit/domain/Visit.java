package com.example.patientmanagementapi.visit.domain;

import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.patient.domain.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 환자방문
 */
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital; // 병원ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient; // 환자ID
    @Column(nullable = false)
    private LocalDateTime receptionDate; // 접수일시
    @Column(length = 10, nullable = false)
    private String visitStatusCode; // 방문상태코드
}
