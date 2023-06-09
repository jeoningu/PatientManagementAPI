package com.example.patientmanagementapi.visit.domain;

import com.example.patientmanagementapi.common.enumTypeConverter.VisitStatusType;
import com.example.patientmanagementapi.common.enumTypeConverter.VisitStatusTypeConverter;
import com.example.patientmanagementapi.hospital.domain.Hospital;
import com.example.patientmanagementapi.patient.domain.Patient;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 환자방문
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime receptionDate; // 접수일시
    @Convert(converter = VisitStatusTypeConverter.class)
    @Column(length = 10, nullable = false)
    private VisitStatusType visitStatusType; // 방문상태코드

    @Builder
    public Visit(Hospital hospital, Patient patient, LocalDateTime receptionDate, VisitStatusType visitStatusType) {
        this.hospital = hospital;
        this.patient = patient;
        this.receptionDate = receptionDate;
        this.visitStatusType = visitStatusType;
    }

    public void update(Hospital hospital, Patient patient, LocalDateTime receptionDate, String visitStatusType) {
        this.hospital = hospital;
        this.patient = patient;
        this.receptionDate = receptionDate;
        this.visitStatusType = VisitStatusType.valueOfCode(visitStatusType);
    }
}
