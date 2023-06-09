package com.example.patientmanagementapi.hospital.controller;

import com.example.patientmanagementapi.config.RestDocsConfiguration;
import com.example.patientmanagementapi.hospital.controller.dto.request.HospitalCreateRequest;
import com.example.patientmanagementapi.hospital.controller.dto.request.HospitalUpdateRequest;
import com.example.patientmanagementapi.hospital.controller.dto.response.HospitalResponse;
import com.example.patientmanagementapi.hospital.service.HospitalService;
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


@WebMvcTest(HospitalController.class)
@Import(RestDocsConfiguration.class) // spring rest docs 설정
@ExtendWith(RestDocumentationExtension.class) // setUp에서 주입받을 RestDocumentationContextProvider를 읽어올 수 있게 한다.
@AutoConfigureRestDocs // Rest docs 자동 설정
class HospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HospitalService hospitalService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .build();
    }

    @Test
    @DisplayName("병원 추가")
    void create() throws Exception {
        final HospitalCreateRequest hospitalCreateRequest = HospitalCreateRequest.builder()
                .hospitalName("병원1")
                .nursingHomeNo("01")
                .hospitalHeadName("병원장1")
                .build();
        final HospitalResponse hospitalResponse = new HospitalResponse(1L, "병원1", "01", "병원장1");
        when(hospitalService.create(any(HospitalCreateRequest.class))).thenReturn(hospitalResponse);

        String requestJson = objectMapper.writeValueAsString(hospitalCreateRequest);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/hospital")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("hospital-create",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("hospitalName").description("병원명"),
                                PayloadDocumentation.fieldWithPath("nursingHomeNo").description("요양기관번호"),
                                PayloadDocumentation.fieldWithPath("hospitalHeadName").description("병원장명")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("hospitalName").description("병원명"),
                                PayloadDocumentation.fieldWithPath("nursingHomeNo").description("요양기관번호"),
                                PayloadDocumentation.fieldWithPath("hospitalHeadName").description("병원장명")
                        )
                ));
    }

    @Test
    @DisplayName("병원 단건 조회")
    void findById() throws Exception {
        final HospitalResponse hospitalResponse = new HospitalResponse(1L, "병원1", "01", "병원장1");
        when(hospitalService.findById(anyLong())).thenReturn(hospitalResponse);

        this.mockMvc.perform(get("/hospital/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("hospital-findById",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("병원ID")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("hospitalName").description("병원명"),
                                PayloadDocumentation.fieldWithPath("nursingHomeNo").description("요양기관번호"),
                                PayloadDocumentation.fieldWithPath("hospitalHeadName").description("병원장명")
                        )
                ));
    }

    @Test
    @DisplayName("병원 전체 조회")
    void findAll() throws Exception{
        final List<HospitalResponse> hospitalResponseList = Lists.newArrayList(
                new HospitalResponse(1L, "병원1", "01", "병원장1"),
                new HospitalResponse(2L, "병원2", "01", "병원장1"),
                new HospitalResponse(3L, "병원3", "02", "병원장2")
        );
        when(hospitalService.findAll()).thenReturn(hospitalResponseList);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/hospital")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("hospital-findAll",
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("[].id").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("[].hospitalName").description("병원명"),
                                PayloadDocumentation.fieldWithPath("[].nursingHomeNo").description("요양기관번호"),
                                PayloadDocumentation.fieldWithPath("[].hospitalHeadName").description("병원장명")
                        )
                ));
    }

    @Test
    @DisplayName("병원 수정")
    void update() throws Exception {
        final HospitalUpdateRequest hospitalUpdateRequest = HospitalUpdateRequest.builder()
                .hospitalName("병원1")
                .nursingHomeNo("01")
                .hospitalHeadName("병원장1")
                .build();
        final HospitalResponse hospitalResponse = new HospitalResponse(1L, "병원1", "01", "병원장1");
        when(hospitalService.update(anyLong(),any(HospitalUpdateRequest.class))).thenReturn(hospitalResponse);

        String requestJson = objectMapper.writeValueAsString(hospitalUpdateRequest);
        this.mockMvc.perform(RestDocumentationRequestBuilders.put("/hospital/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("hospital-update",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("병원ID")
                        ),
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("hospitalName").description("병원명"),
                                PayloadDocumentation.fieldWithPath("nursingHomeNo").description("요양기관번호"),
                                PayloadDocumentation.fieldWithPath("hospitalHeadName").description("병원장명")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").description("병원ID"),
                                PayloadDocumentation.fieldWithPath("hospitalName").description("병원명"),
                                PayloadDocumentation.fieldWithPath("nursingHomeNo").description("요양기관번호"),
                                PayloadDocumentation.fieldWithPath("hospitalHeadName").description("병원장명")
                        )
                ));
    }

    @Test
    @DisplayName("병원 삭제")
    void delete() throws Exception {
        this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/hospital/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("hospital-delete",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("id").description("병원ID")
                        )
                ));
    }
}