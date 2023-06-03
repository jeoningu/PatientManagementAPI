package com.example.patientmanagementapi.hospital.domain;

import javax.persistence.*;

/**
 * 병원
 */
@Entity
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Hospital patient;
    @Column(length = 45, nullable = false)
    private String hospitalName; // 병원명
    @Column(length = 20, nullable = false)
    private String nursingHomeNo; // 요양기관번호
    @Column(length = 10, nullable = false)
    private String hospitalHeadName; // 병원장명



}
