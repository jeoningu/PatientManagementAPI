package com.example.patientmanagementapi.patient.repository;

import com.example.patientmanagementapi.patient.controller.dto.response.PatientFindAllResponse;
import com.example.patientmanagementapi.patient.domain.Patient;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.patientmanagementapi.patient.domain.QPatient.patient;
import static com.example.patientmanagementapi.visit.domain.QVisit.visit;

public class PatientRepositoryImpl extends QuerydslRepositorySupport implements PatientCustomRepository {

    @Autowired
    private JPAQueryFactory queryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public PatientRepositoryImpl() {
        super(Patient.class);
    }

    @Override
    public Page<PatientFindAllResponse> findBySearchOption(Pageable pageable, String patientName, String patientNo, String birth) {
        JPQLQuery<Patient> query = queryFactory.selectFrom(patient)
                .leftJoin(patient.visits, visit).fetchJoin()
                .where(containPatientName(patientName), containPatientNo(patientNo), containBirth(birth))
                //.orderBy(visit.receptionDate.desc().nullsLast()) // 최근방문일 기준으로 정렬
                .distinct();

        List<Patient> patientList = this.getQuerydsl().applyPagination(pageable, query).fetch();

        List<PatientFindAllResponse> patientFindAllResponseList = patientList.stream()
                .map(PatientFindAllResponse::new)
                .collect(Collectors.toList());

        return new PageImpl<PatientFindAllResponse>(patientFindAllResponseList, pageable, query.fetchCount());
    }

    private BooleanExpression containPatientName(String patientName) {
        if(patientName == null || patientName.isEmpty()) {
            return null;
        }
        return patient.patientName.containsIgnoreCase(patientName);
    }

    private BooleanExpression containPatientNo(String patientNo) {
        if(patientNo == null || patientNo.isEmpty()) {
            return null;
        }
        return patient.patientNo.containsIgnoreCase(patientNo);
    }

    private BooleanExpression containBirth(String birth) {
        if(birth == null || birth.isEmpty()) {
            return null;
        }
        return patient.birth.containsIgnoreCase(birth);
    }
}
