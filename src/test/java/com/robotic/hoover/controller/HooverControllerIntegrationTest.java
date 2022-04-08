package com.robotic.hoover.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotic.hoover.data.TestData;
import com.robotic.hoover.dto.response.NavigateResponseDto;
import com.robotic.hoover.errorHandling.ErrorCode;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HooverControllerIntegrationTest {

    private static final String NAVIGATE_ENDPOINT = "/hoover/navigate";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void return_bad_request_invalid_instructions() throws Exception {
        String json = objectMapper.writeValueAsString(TestData.wrongInstructionInputRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void return_bad_request_invalid_room_size_and_coordinate_input() throws Exception {
        String json = objectMapper.writeValueAsString(TestData.invalidRoomSizeCoordinateRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void return_bad_request_mandatory_args_not_present() throws Exception {
        String json = objectMapper.writeValueAsString(TestData.missingArgRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void return_bad_request_wrong_json_format() throws Exception {
        String json = objectMapper.writeValueAsString(TestData.validNavigateRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                .content("{" + json + "test,}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void return_un_processable_entity_negative_coordinates() throws Exception {
        String json = objectMapper.writeValueAsString(TestData.negativeCoordinateRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCode.INVALID_COORDINATE_VALUE.getErrorCode()));
    }

    @Test
    public void return_un_processable_entity_negative_number_coordinate() throws Exception {
        String json = objectMapper.writeValueAsString(TestData.negativeCoordinateRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCode.INVALID_COORDINATE_VALUE.getErrorCode()));
    }

    @Test
    public void return_un_processable_entity_invalid_coordinate_value() throws Exception {
        String json = objectMapper.writeValueAsString(TestData.invalidCoordinateRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(ErrorCode.INVALID_COORDINATE_VALUE.getErrorCode()));
    }

    @Test
    public void navigate_success_return_navigate_result_ok() throws Exception {
        String requestJson = objectMapper.writeValueAsString(TestData.validNavigateRequestDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                ).andExpect(status().isOk())
                .andReturn();
        NavigateResponseDto navigateResponseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), NavigateResponseDto.class);
        Assert.assertEquals(1, navigateResponseDto.getPatches());
        Assert.assertEquals("1", navigateResponseDto.getCoords().get(0).toString());
        Assert.assertEquals("3", navigateResponseDto.getCoords().get(1).toString());

    }

    @Test
    public void navigate_success_return_navigate_result_skid_wall_ok() throws Exception {
        String requestJson = objectMapper.writeValueAsString(TestData.validSkidWallRequestDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(NAVIGATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                ).andExpect(status().isOk())
                .andReturn();
        NavigateResponseDto navigateResponseDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), NavigateResponseDto.class);
        Assert.assertEquals(1, navigateResponseDto.getPatches());
        Assert.assertEquals("0", navigateResponseDto.getCoords().get(0).toString());
        Assert.assertEquals("3", navigateResponseDto.getCoords().get(1).toString());

    }
}
