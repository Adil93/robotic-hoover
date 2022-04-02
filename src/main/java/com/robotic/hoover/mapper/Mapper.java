package com.robotic.hoover.mapper;

import com.robotic.hoover.entity.NavigateRequest;
import com.robotic.hoover.entity.NavigateResult;
import com.robotic.hoover.dto.Coordinate;
import com.robotic.hoover.dto.request.NavigateRequestDto;
import com.robotic.hoover.dto.response.NavigateResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class Mapper implements IMapper {

    public NavigateRequest mapToNavigateRequest(NavigateRequestDto navigateRequestDto) {
        NavigateRequest navigateRequest = new NavigateRequest();
        navigateRequest.setMaxRoomCoordinate(
                new Coordinate(navigateRequestDto.getRoomSize().get(0), navigateRequestDto.getRoomSize().get(1)));
        navigateRequest.setCurrentCoordinate(
                new Coordinate(navigateRequestDto.getCoords().get(0), navigateRequestDto.getCoords().get(1)));

        List<Coordinate> patches = navigateRequestDto.getPatches().stream().map(patch -> new Coordinate(patch.get(0), patch.get(1))).collect(Collectors.toList());
        navigateRequest.setPatches(new HashSet<>(patches));
        navigateRequest.setInstructions(navigateRequestDto.getInstructions());
        return navigateRequest;
    }

    public NavigateResponseDto mapToNavigateResponseDto(NavigateResult navigateResult) {
        NavigateResponseDto navigateResponseDto = new NavigateResponseDto();
        navigateResponseDto.setCoords(Arrays.asList(navigateResult.getFinalPosition().getX(), navigateResult.getFinalPosition().getY()));
        navigateResponseDto.setPatches(navigateResult.getNoOfPatchesCovered());
        return navigateResponseDto;
    }
}
