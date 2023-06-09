package com.example.patientmanagementapi.patient.controller;

import com.example.patientmanagementapi.config.RestDocsConfiguration;
import com.example.patientmanagementapi.hospital.controller.dto.request.HospitalUpdateRequest;
import com.example.patientmanagementapi.hospital.controller.dto.response.HospitalResponse;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientCreateRequest;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientUpdateRequest;
import com.example.patientmanagementapi.patient.controller.dto.response.PatientResponse;
import com.example.patientmanagementapi.patient.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
@Import(RestDocsConfiguration.class) // spring rest docs 설정
@ExtendWith(RestDocumentationExtension.class) // setUp에서 주입받을 RestDocumentationContextProvider를 읽어올 수 있게 한다.
@AutoConfigureRestDocs // Rest docs 자동 설정
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private PatientService patientService;
    

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("환자 추가")
    void create() throws Exception {
        final PatientCreateRequest patientCreateRequest = PatientCreateRequest.builder()
                .hospitalId("1")
                .patientName("환자1")
                .genderType("M")
                .birth("19990101")
                .phone("01012341234")
                .build();

        final PatientResponse patientResponse = new PatientResponse(1L, 1L, "환자1", "1", "M", "19990101", "01012341234");
        when(patientService.create(any(PatientCreateRequest.class))).thenReturn(patientResponse);

        String requestJson = objectMapper.writeValueAsString(patientCreateRequest);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/patient")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("patient-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("patientNo").description("환자등록번호"),
                                PayloadDocumentation.fieldWithPath("genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호")
                        )
                ));
    }

    @Test
    @DisplayName("환자 단건 조회")
    void findById() throws Exception {
        final PatientResponse patientResponse = new PatientResponse(1L, 1L, "환자1", "1", "M", "19990101", "01012341234");
        when(patientService.findById(anyLong())).thenReturn(patientResponse);

        this.mockMvc.perform(get("/patient/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient-findById",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("환자ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("patientNo").description("환자등록번호"),
                                PayloadDocumentation.fieldWithPath("genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호")
                        )
                ));
    }

    @Test
    @DisplayName("환자 전체 조회")
    void findAll() throws Exception {
        final List<PatientResponse> patientResponseList = Lists.newArrayList(
                new PatientResponse(1L, 1L, "환자1", "1", "M", "19990101", "01012341234"),
                new PatientResponse(2L, 1L, "환자2", "1", "F", "19990102", "01012341236"),
                new PatientResponse(3L, 1L, "환자3", "1", "H", "19990104", "01012341236")
        );
        when(patientService.findAll()).thenReturn(patientResponseList);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/patient")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient-findAll",
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("[].id").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("[].hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("[].patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("[].patientNo").description("환자등록번호"),
                                PayloadDocumentation.fieldWithPath("[].genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("[].birth").description("생년월일"),
                                PayloadDocumentation.fieldWithPath("[].phone").description("휴대전화번호")
                        )
                ));
    }

    @Test
    @DisplayName("환자 수정")
    void update() throws Exception {
        final PatientUpdateRequest patientUpdateRequest = PatientUpdateRequest.builder()
                .hospitalId("1")
                .patientName("환자1")
                .genderType("M")
                .birth("19990101")
                .phone("01012341234")
                .build();
        final PatientResponse patientResponse = new PatientResponse(1L, 1L, "환자1", "1", "M", "19990101", "01012341234");
        when(patientService.update(anyLong(),any(PatientUpdateRequest.class))).thenReturn(patientResponse);

        String requestJson = objectMapper.writeValueAsString(patientUpdateRequest);
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/patient/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient-update",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("환자ID")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("patientNo").description("환자등록번호"),
                                PayloadDocumentation.fieldWithPath("genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호")
                        )
                ));
    }

    @Test
    @DisplayName("환자 삭제")
    void delete() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/patient/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient-delete",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("환자ID")
                        )
                ));
    }
}