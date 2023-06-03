package com.example.patientmanagementapi.patient.domain;

import com.example.patientmanagementapi.common.typeConverter.GenderType;
import com.example.patientmanagementapi.common.typeConverter.GenderTypeConverter;
import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.visit.domain.Visit;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 환자
 */
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "patient")
    private List<Visit> visits = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;
    @Column(length = 45, nullable = false)
    private String patientName; // 환자명
    @Column(length = 13, nullable = false)
    private String patientNo; // 환자등록번호
    @Convert(converter = GenderTypeConverter.class)
    @Column(length = 10, nullable = false)
    private GenderType genderType; // 성별코드
    @Column(length = 10)
    private String birth; // 생년월일
    @Column(length = 20)
    private String phone; // 휴대전화번호

}
