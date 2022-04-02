package com.robotic.hoover.service.impl;

import com.robotic.hoover.dto.Coordinate;
import com.robotic.hoover.dto.request.NavigateRequestDto;
import com.robotic.hoover.dto.response.NavigateResponseDto;
import com.robotic.hoover.entity.NavigateRequest;
import com.robotic.hoover.entity.NavigateResult;
import com.robotic.hoover.errorHandling.ErrorCode;
import com.robotic.hoover.errorHandling.exception.UnProcessableEntityException;
import com.robotic.hoover.mapper.IMapper;
import com.robotic.hoover.repository.NavigateRequestRepository;
import com.robotic.hoover.repository.NavigateResultRepository;
import com.robotic.hoover.service.IHooverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class HooverService implements IHooverService {

    private IMapper mapper;

    private NavigateRequestRepository navigateRequestRepository;
    private NavigateResultRepository navigateResultRepository;

    @Override
    public NavigateResponseDto navigate(NavigateRequestDto navigateRequestDto) throws UnProcessableEntityException {
        validateRequest(navigateRequestDto);
        NavigateRequest createdRequest = navigateRequestRepository.save(mapper.mapToNavigateRequest(navigateRequestDto));
        return mapper.mapToNavigateResponseDto(navigate(createdRequest));
    }

    @Override
    public NavigateResult navigate(NavigateRequest request) {

        Set<Coordinate> visitedPatches = new HashSet<>(request.getPatches().size());
        int instructionsLen = request.getInstructions().length();
        Coordinate currentCoordinate = request.getCurrentCoordinate();
        Coordinate finalCoordinate = new Coordinate(currentCoordinate.getX(), currentCoordinate.getY());

        for (int i = 0; i < instructionsLen; i++) {
            char move = request.getInstructions().charAt(i);
            Coordinate movingCoordinate = getMovingCoordinate(finalCoordinate, move);
            if (canMove(finalCoordinate, movingCoordinate, request.getMaxRoomCoordinate())) {
                finalCoordinate = movingCoordinate;
                if (request.getPatches().contains(movingCoordinate)) {
                    visitedPatches.add(movingCoordinate);
                }
            }
        }
        return navigateResultRepository.save(new NavigateResult(request, finalCoordinate, visitedPatches.size()));
    }

    private void validateRequest(NavigateRequestDto navigateRequestDto) throws UnProcessableEntityException {
        if (!(navigateRequestDto.getRoomSize().stream().allMatch(point -> point >= 0)
                && navigateRequestDto.getCoords().stream().allMatch(coord -> coord >= 0)
                && navigateRequestDto.getCoords().get(0) <= navigateRequestDto.getRoomSize().get(0)
                && navigateRequestDto.getCoords().get(1) <= navigateRequestDto.getRoomSize().get(1))) {
            throw new UnProcessableEntityException(ErrorCode.INVALID_COORDINATE_VALUE);
        }

    }

    private Coordinate getMovingCoordinate(Coordinate currentCoordinate, char move) {
        Coordinate movingCoordinate = new Coordinate(currentCoordinate.getX(), currentCoordinate.getY());

        switch (move) {
            case 'N':
                movingCoordinate.setY(currentCoordinate.getY() + 1);
                break;
            case 'E':
                movingCoordinate.setX(currentCoordinate.getX() + 1);
                break;
            case 'S':
                movingCoordinate.setY(currentCoordinate.getY() - 1);
                break;
            case 'W':
                movingCoordinate.setX(currentCoordinate.getX() - 1);
                break;
            default:
                log.warn("Invalid Input");
        }
        return movingCoordinate;
    }

    private boolean canMove(Coordinate currentCoordinate, Coordinate movingCoordinate, Coordinate maxRoomCoordinate) {
        return !currentCoordinate.equals(movingCoordinate)
                && movingCoordinate.getX() <= maxRoomCoordinate.getX()
                && movingCoordinate.getY() <= maxRoomCoordinate.getY()
                && movingCoordinate.getX() >= 0 && movingCoordinate.getY() >= 0;
    }

}
