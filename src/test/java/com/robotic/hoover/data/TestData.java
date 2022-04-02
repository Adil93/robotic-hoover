package com.robotic.hoover.data;

import com.robotic.hoover.dto.request.NavigateRequestDto;

import java.util.Arrays;

public class TestData {
    private static final String SAMPLE_VALID_INSTRUCTION = "NNESEESWNWW";

    public static NavigateRequestDto validNavigateRequestDto = new NavigateRequestDto(
            Arrays.asList(5, 5),
            Arrays.asList(1, 2),
            Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)),
            SAMPLE_VALID_INSTRUCTION
    );

    public static NavigateRequestDto negativeCoordinateRequestDto = new NavigateRequestDto(
            Arrays.asList(5, 5),
            Arrays.asList(1, -2),
            Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)),
            SAMPLE_VALID_INSTRUCTION
    );

    public static NavigateRequestDto invalidCoordinateRequestDto = new NavigateRequestDto(
            Arrays.asList(5, 5),
            Arrays.asList(1, 7),
            Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)),
            SAMPLE_VALID_INSTRUCTION
    );

    public static NavigateRequestDto invalidRoomSizeCoordinateRequestDto = new NavigateRequestDto(
            Arrays.asList(5, 5,6),
            Arrays.asList(1, 2),
            Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)),
            SAMPLE_VALID_INSTRUCTION
    );

    public static NavigateRequestDto wrongInstructionInputRequestDto = new NavigateRequestDto(
            Arrays.asList(5, 5),
            Arrays.asList(1, 2),
            Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)),
            SAMPLE_VALID_INSTRUCTION + "RR"
    );

    public static NavigateRequestDto missingArgRequestDto = new NavigateRequestDto(
            null,
            null,
            Arrays.asList(Arrays.asList(1, 0), Arrays.asList(2, 2), Arrays.asList(2, 3)),
            SAMPLE_VALID_INSTRUCTION + "RR"
    );
}
