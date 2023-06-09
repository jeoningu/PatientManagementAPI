package com.example.patientmanagementapi.patient.domain;

import com.example.patientmanagementapi.common.enumTypeConverter.GenderType;
import com.example.patientmanagementapi.common.enumTypeConverter.GenderTypeConverter;
import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.visit.domain.Visit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 환자
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 환자ID
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Visit> visits = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital; // 병원ID
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

    @Builder
    public Patient(Hospital hospital, String patientName, String patientNo, GenderType genderType, String birth, String phone) {
        this.hospital = hospital;
        this.patientName = patientName;
        this.patientNo = patientNo;
        this.genderType = genderType;
        this.birth = birth;
        this.phone = phone;
    }

    public void update(Hospital hospital, String patientName, String genderType, String birth, String phone) {
        this.hospital = hospital;
        this.patientName = patientName;
        this.genderType = GenderType.valueOfCode(genderType);
        this.birth = birth;
        this.phone = phone;
    }
}
