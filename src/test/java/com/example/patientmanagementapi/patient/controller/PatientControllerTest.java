package com.example.patientmanagementapi.patient.controller;

import com.example.patientmanagementapi.config.RestDocsConfiguration;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientCreateRequest;
import com.example.patientmanagementapi.patient.controller.dto.request.PatientUpdateRequest;
import com.example.patientmanagementapi.patient.controller.dto.response.PatientFindAllResponse;
import com.example.patientmanagementapi.patient.controller.dto.response.PatientResponse;
import com.example.patientmanagementapi.patient.service.PatientService;
import com.example.patientmanagementapi.visit.controller.dto.response.VisitResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
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

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
                .build();
    }

    @Test
    @DisplayName("환자 추가")
    void create() throws Exception {
        final PatientCreateRequest patientCreateRequest = PatientCreateRequest.builder()
                .hospitalId("1")
                .patientName("환자1")
                .genderType("M")
                .birth("1999-01-01")
                .phone("010-1234-1234")
                .build();

        final PatientResponse patientResponse = new PatientResponse(1L, 1L, "환자1", "1", "M", "1999-01-01", "010-1234-1234");
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
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일 (yyyy-MM-dd)"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호 (000-0000-0000)")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("patientNo").description("환자등록번호"),
                                PayloadDocumentation.fieldWithPath("genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일 (yyyy-MM-dd)"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호 (000-0000-0000)"),
                                PayloadDocumentation.fieldWithPath("visits").description("환자방문 목록")
                        )
                ));
    }

    @Test
    @DisplayName("환자 단건 조회")
    void findById() throws Exception {
        final List<VisitResponse> visitResponseList = Lists.newArrayList(
                new VisitResponse(1L, 1L, 1L, LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER), "1"),
                new VisitResponse(2L, 2L, 1L, LocalDateTime.parse("2023-06-09 12:01:03", DATE_TIME_FORMATTER), "2"),
                new VisitResponse(3L, 3L, 1L, LocalDateTime.parse("2023-06-09 12:05:00", DATE_TIME_FORMATTER), "3")
        );
        final PatientResponse patientResponse = new PatientResponse(1L, 1L, "환자1", "1", "M", "1999-01-01", "010-1234-1234", visitResponseList);
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
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일 (yyyy-MM-dd)"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호 (000-0000-0000)"),
                                PayloadDocumentation.fieldWithPath("visits").description("환자방문 목록"),
                                PayloadDocumentation.fieldWithPath("visits[].id").description("환자방문ID"),
                                PayloadDocumentation.fieldWithPath("visits[].hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("visits[].patientId").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("visits[].receptionDate").description("접수일시 (yyyy-MM-dd'T'HH:mm:ss)"),
                                PayloadDocumentation.fieldWithPath("visits[].visitStatusType").description("방문상태코드 (1: 방문중 | 2:종료 | 3:취소)")
                        )
                ));
    }

    @Test
    @DisplayName("환자 전체 조회")
    void findAll() throws Exception {
        final List<PatientFindAllResponse> patientFindAllResponseList = Lists.newArrayList(
                new PatientFindAllResponse(1L, 1L, "환자1", "1", "M", "1999-01-01", "010-1234-1234", LocalDateTime.parse("2023-06-09 12:00:00", DATE_TIME_FORMATTER)),
                new PatientFindAllResponse(2L, 1L, "환자2", "1", "F", "1999-01-02", "010-1234-1234", LocalDateTime.parse("2023-06-09 09:00:00", DATE_TIME_FORMATTER)),
                new PatientFindAllResponse(3L, 1L, "환자3", "1", "H", "1999-01-04", "010-1234-1234", LocalDateTime.parse("2023-06-09 09:30:00", DATE_TIME_FORMATTER))
        );

        Page<PatientFindAllResponse> patientResponsePage = new PageImpl<>(patientFindAllResponseList);
        when(patientService.findAll(any(Pageable.class), anyString(), anyString(), anyString())).thenReturn(patientResponsePage);

        String encodedPatientName = URLEncoder.encode("환자1", String.valueOf(StandardCharsets.UTF_8));
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/patient")
                        .param("pageSize", "10")
                        .param("pageNo", "1")
                        .param("patientName", encodedPatientName)
                        .param("patientNo", "202300001")
                        .param("birth", "1999-01-01")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patient-findAll",
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("pageSize").description("한 번에 조회하는 최대 항목 수 (기본값: 10)"),
                                RequestDocumentation.parameterWithName("pageNo").description("조회할 페이지 번호 (1부터 시작, 기본값: 1)"),
                                RequestDocumentation.parameterWithName("patientName").optional().description("환자명"),
                                RequestDocumentation.parameterWithName("patientNo").optional().description("환자등록번호"),
                                RequestDocumentation.parameterWithName("birth").optional().description("생년월일 (yyyy-MM-dd)")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("content[].id").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("content[].hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("content[].patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("content[].patientNo").description("환자등록번호"),
                                PayloadDocumentation.fieldWithPath("content[].genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("content[].birth").description("생년월일 (yyyy-MM-dd)"),
                                PayloadDocumentation.fieldWithPath("content[].phone").description("휴대전화번호 (000-0000-0000)"),
                                PayloadDocumentation.fieldWithPath("content[].recentVisitDate").description("최근방문일"),
                                PayloadDocumentation.fieldWithPath("pageable").ignored(),
                                PayloadDocumentation.fieldWithPath("totalElements").ignored(),
                                PayloadDocumentation.fieldWithPath("totalPages").ignored(),
                                PayloadDocumentation.fieldWithPath("last").ignored(),
                                PayloadDocumentation.fieldWithPath("size").ignored(),
                                PayloadDocumentation.fieldWithPath("number").ignored(),
                                PayloadDocumentation.fieldWithPath("numberOfElements").ignored(),
                                PayloadDocumentation.fieldWithPath("first").ignored(),
                                PayloadDocumentation.fieldWithPath("sort.empty").ignored(),
                                PayloadDocumentation.fieldWithPath("sort.sorted").ignored(),
                                PayloadDocumentation.fieldWithPath("sort.unsorted").ignored(),
                                PayloadDocumentation.fieldWithPath("empty").ignored()
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
                .birth("1999-01-01")
                .phone("010-1234-1234")
                .build();
        final PatientResponse patientResponse = new PatientResponse(1L, 1L, "환자1", "1", "M", "1999-01-01", "010-1234-1234");
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
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일 (yyyy-MM-dd)"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호 (000-0000-0000)")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("환자ID"),
                                PayloadDocumentation.fieldWithPath("hospitalId").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("patientName").description("환자명"),
                                PayloadDocumentation.fieldWithPath("patientNo").description("환자등록번호"),
                                PayloadDocumentation.fieldWithPath("genderType").description("성별 (M:남자 | F:여자 | H:모름)"),
                                PayloadDocumentation.fieldWithPath("birth").description("생년월일 (yyyy-MM-dd)"),
                                PayloadDocumentation.fieldWithPath("phone").description("휴대전화번호 (000-0000-0000)"),
                                PayloadDocumentation.fieldWithPath("visits").description("환자방문 목록")
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