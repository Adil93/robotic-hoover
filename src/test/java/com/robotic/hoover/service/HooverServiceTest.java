package com.robotic.hoover.service;

import com.robotic.hoover.data.TestData;
import com.robotic.hoover.dto.Coordinate;
import com.robotic.hoover.dto.request.NavigateRequestDto;
import com.robotic.hoover.dto.response.NavigateResponseDto;
import com.robotic.hoover.entity.NavigateRequest;
import com.robotic.hoover.entity.NavigateResult;
import com.robotic.hoover.errorHandling.ErrorCode;
import com.robotic.hoover.errorHandling.exception.UnProcessableEntityException;
import com.robotic.hoover.mapper.Mapper;
import com.robotic.hoover.repository.NavigateRequestRepository;
import com.robotic.hoover.repository.NavigateResultRepository;
import com.robotic.hoover.service.impl.HooverService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HooverServiceTest {
    private static final String SAMPLE_VALID_INSTRUCTION = "NNESEESWNWW";

    @Mock
    private NavigateRequestRepository navigateRequestRepository;

    @Mock
    private NavigateResultRepository navigateResultRepository;

    @InjectMocks
    private HooverService hooverService;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(hooverService, "mapper", new Mapper());
    }

    @Test
    void negative_coordinate_throw_un_processable_entity_exception() {
        final Long savedNavigateRequestId = 1234l;
        final Long savedNavigateResultId = 3214l;


        NavigateRequest navigateRequest = new Mapper().mapToNavigateRequest(TestData.negativeCoordinateRequestDto);
        navigateRequest.setId(savedNavigateRequestId);

        NavigateResult navigateResult = new NavigateResult(navigateRequest, new Coordinate(1, 3), 1);
        navigateResult.setId(savedNavigateResultId);

        final UnProcessableEntityException exception = assertThrows(
                UnProcessableEntityException.class,
                () -> hooverService.navigate(TestData.negativeCoordinateRequestDto)
        );

        Assert.assertEquals(exception.getErrorCode(), ErrorCode.INVALID_COORDINATE_VALUE.getErrorCode());
    }

    @Test
    void current_coordinate_greater_than_room_size_throw_un_processable_entity_exception() {
        final Long savedNavigateRequestId = 1234l;
        final Long savedNavigateResultId = 3214l;

        NavigateRequest navigateRequest = new Mapper().mapToNavigateRequest(TestData.invalidCoordinateRequestDto);
        navigateRequest.setId(savedNavigateRequestId);

        NavigateResult navigateResult = new NavigateResult(navigateRequest, new Coordinate(1, 3), 1);
        navigateResult.setId(savedNavigateResultId);

        final UnProcessableEntityException exception = assertThrows(
                UnProcessableEntityException.class,
                () -> hooverService.navigate(TestData.invalidCoordinateRequestDto)
        );

        Assert.assertEquals(exception.getErrorCode(), ErrorCode.INVALID_COORDINATE_VALUE.getErrorCode());
    }

    @Test
    void navigate_success_navigate_request_dto() throws UnProcessableEntityException {
        final Long savedNavigateRequestId = 1234l;
        final Long savedNavigateResultId = 3214l;

        NavigateRequest navigateRequest = new Mapper().mapToNavigateRequest(TestData.validNavigateRequestDto);
        navigateRequest.setId(savedNavigateRequestId);

        NavigateResult navigateResult = new NavigateResult(navigateRequest, new Coordinate(1, 3), 1);
        navigateResult.setId(savedNavigateResultId);

        when(navigateRequestRepository.save(ArgumentMatchers.any(NavigateRequest.class))).thenReturn(navigateRequest);
        when(navigateResultRepository.save(ArgumentMatchers.any(NavigateResult.class))).thenReturn(navigateResult);

        NavigateResponseDto navigateResponseDto = hooverService.navigate(TestData.validNavigateRequestDto);

        Assert.assertEquals(1, navigateResponseDto.getPatches());

    }

    @Test
    void navigate_success_navigate_request() throws UnProcessableEntityException {
        final Long savedNavigateRequestId = 123l;
        final Long savedNavigateResultId = 321l;
        NavigateRequestDto navigateRequestDto = new NavigateRequestDto();
        navigateRequestDto.setInstructions(SAMPLE_VALID_INSTRUCTION);
        navigateRequestDto.setRoomSize(Arrays.asList(5, 5));
        navigateRequestDto.setCoords(Arrays.asList(1, 2));
        navigateRequestDto.setPatches(Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)));


        NavigateRequest navigateRequest = new Mapper().mapToNavigateRequest(TestData.validNavigateRequestDto);
        NavigateResult navigateResult = new NavigateResult(navigateRequest, new Coordinate(1, 3), 1);

        navigateRequest.setId(savedNavigateRequestId);
        navigateResult.setId(savedNavigateResultId);

        when(navigateResultRepository.save(ArgumentMatchers.any(NavigateResult.class))).thenReturn(navigateResult);
        NavigateResult navigateResult1 = hooverService.navigate(navigateRequest);

        Assert.assertEquals(1, navigateResult1.getNoOfPatchesCovered());
        Assert.assertEquals(navigateRequest.getInstructions(), navigateResult1.getNavigateRequest().getInstructions());

    }

    @Test
    void navigate_success_navigate_request_skidding_the_wall() throws UnProcessableEntityException {
        final Long savedNavigateRequestId = 123l;
        final Long savedNavigateResultId = 321l;
        NavigateRequestDto navigateRequestDto = new NavigateRequestDto();
        navigateRequestDto.setInstructions(SAMPLE_VALID_INSTRUCTION);
        navigateRequestDto.setRoomSize(Arrays.asList(5, 5));
        navigateRequestDto.setCoords(Arrays.asList(1, 2));
        navigateRequestDto.setPatches(Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)));


        NavigateRequest navigateRequest = new Mapper().mapToNavigateRequest(TestData.validSkidWallRequestDto);
        NavigateResult navigateResult = new NavigateResult(navigateRequest, new Coordinate(0, 3), 1);

        navigateRequest.setId(savedNavigateRequestId);
        navigateResult.setId(savedNavigateResultId);

        when(navigateResultRepository.save(ArgumentMatchers.any(NavigateResult.class))).thenReturn(navigateResult);
        NavigateResult navigateResult1 = hooverService.navigate(navigateRequest);

        Assert.assertEquals(1, navigateResult1.getNoOfPatchesCovered());
        Assert.assertEquals(0, navigateResult1.getFinalPosition().getX());
        Assert.assertEquals(3, navigateResult1.getFinalPosition().getY());
        Assert.assertEquals(navigateRequest.getInstructions(), navigateResult1.getNavigateRequest().getInstructions());

    }

}
