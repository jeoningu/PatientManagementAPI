package com.example.patientmanagementapi.visit.controller;

import com.example.patientmanagementapi.config.RestDocsConfiguration;
import com.example.patientmanagementapi.visit.controller.dto.request.VisitCreateRequest;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientUpdateRequest;
import com.example.patientmanagementapi.visit.controller.dto.request.VisitUpdateRequest;
import com.example.patientmanagementapi.visit.controller.dto.response.VisitResponse;
import com.example.patientmanagementapi.visit.service.VisitService;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
@Import(RestDocsConfiguration.class) // spring rest docs 설정
@ExtendWith(RestDocumentationExtension.class) // setUp에서 주입받을 RestDocumentationContextProvider를 읽어올 수 있게 한다.
@AutoConfigureRestDocs // Rest docs 자동 설정
class VisitControllerTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VisitService visitService;


    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("환자방문 추가")
    void create() throws Exception {
        final VisitCreateRequest visitCreateRequest = VisitCreateRequest.builder()
                .hospitalId("1")
                .patientId("1")
                .receptionDate(LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER))
                .visitStatusType("1")
                .build();
        
        final VisitResponse visitResponse = new VisitResponse(1L, 1L, 1L, LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER), "1");
        when(visitService.create(any(VisitCreateRequest.class))).thenReturn(visitResponse);

        String requestJson = objectMapper.writeValueAsString(visitCreateRequest);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/visit")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("visit-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientId").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("receptionDate").description("접수일시 (yyyy-MM-dd HH:mm:ss)"),
                                PayloadDocumentation.fieldWithPath("visitStatusType").description("방문상태코드 (1: 방문중 | 2:종료 | 3:취소)")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자방문ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientId").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("receptionDate").description("접수일시 (yyyy-MM-dd'T'HH:mm:ss)"),
                                PayloadDocumentation.fieldWithPath("visitStatusType").description("방문상태코드 (1: 방문중 | 2:종료 | 3:취소)")
                        )
                ));
    }

    @Test
    @DisplayName("환자방문 단건 조회")
    void findById() throws Exception {
        final VisitResponse visitResponse = new VisitResponse(1L, 1L, 1L, LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER), "1");
        when(visitService.findById(anyLong())).thenReturn(visitResponse);

        this.mockMvc.perform(get("/visit/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("visit-findById",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("환자방문ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자방문ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientId").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("receptionDate").description("접수일시 (yyyy-MM-dd'T'HH:mm:ss)"),
                                PayloadDocumentation.fieldWithPath("visitStatusType").description("방문상태코드 (1: 방문중 | 2:종료 | 3:취소)")
                        )
                ));
    }

    @Test
    @DisplayName("환자방문 전체 조회")
    void findAll() throws Exception {
        final List<VisitResponse> visitResponseList = Lists.newArrayList(
                new VisitResponse(1L, 1L, 1L, LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER), "1"),
                new VisitResponse(2L, 2L, 1L, LocalDateTime.parse("2023-06-09 12:01:03", DATE_TIME_FORMATTER), "2"),
                new VisitResponse(3L, 3L, 1L, LocalDateTime.parse("2023-06-09 12:05:00", DATE_TIME_FORMATTER), "3")
        );
        when(visitService.findAll()).thenReturn(visitResponseList);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/visit")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("visit-findAll",
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("[]id").description("환자방문ID"),
                                PayloadDocumentation.fieldWithPath("[]hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("[]patientId").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("[]receptionDate").description("접수일시 (yyyy-MM-dd'T'HH:mm:ss)"),
                                PayloadDocumentation.fieldWithPath("[]visitStatusType").description("방문상태코드 (1: 방문중 | 2:종료 | 3:취소)")
                        )
                ));
    }

    @Test
    @DisplayName("환자방문 수정")
    void update() throws Exception {
        final VisitUpdateRequest visitUpdateRequest = VisitUpdateRequest.builder()
                .hospitalId("1")
                .patientId("1")
                .receptionDate(LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER))
                .visitStatusType("1")
                .build();

        final VisitResponse visitResponse = new VisitResponse(1L, 1L, 1L, LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER), "1");
        when(visitService.update(anyLong(),any(VisitUpdateRequest.class))).thenReturn(visitResponse);

        String requestJson = objectMapper.writeValueAsString(visitUpdateRequest);
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/visit/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("visit-update",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("환자방문ID")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientId").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("receptionDate").description("접수일시 (yyyy-MM-dd HH:mm:ss)"),
                                PayloadDocumentation.fieldWithPath("visitStatusType").description("방문상태코드 (1: 방문중 | 2:종료 | 3:취소)")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자방문ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientId").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("receptionDate").description("접수일시 (yyyy-MM-dd'T'HH:mm:ss)"),
                                PayloadDocumentation.fieldWithPath("visitStatusType").description("방문상태코드 (1: 방문중 | 2:종료 | 3:취소)")
                        )
                ));
    }

    @Test
    @DisplayName("환자방문 삭제")
    void delete() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/visit/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("visit-delete",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("환자ID")
                        )
                ));
    }
}