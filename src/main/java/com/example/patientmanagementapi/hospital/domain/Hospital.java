package com.example.patientmanagementapi.hospital.domain;

import lombok.*;

import javax.persistence.*;

/**
 * 병원
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // 병원ID
    @Column(length = 45, nullable = false)
    private String hospitalName; // 병원명
    @Column(length = 20, nullable = false)
    private String nursingHomeNo; // 요양기관번호
    @Column(length = 10, nullable = false)
    private String hospitalHeadName; // 병원장명

    @Builder
    public Hospital(String hospitalName, String nursingHomeNo, String hospitalHeadName) {
        this.hospitalName = hospitalHeadName;
        this.nursingHomeNo = nursingHomeNo;
        this.hospitalHeadName = hospitalHeadName;
    }

    public void update(String hospitalName, String nursingHomeNo, String hospitalHeadName) {
        this.hospitalName = hospitalHeadName;
        this.nursingHomeNo = nursingHomeNo;
        this.hospitalHeadName = hospitalHeadName;
    }

}
