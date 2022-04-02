package com.robotic.hoover.mapper;

import com.robotic.hoover.dto.request.NavigateRequestDto;
import com.robotic.hoover.dto.response.NavigateResponseDto;
import com.robotic.hoover.entity.NavigateRequest;
import com.robotic.hoover.entity.NavigateResult;

public interface IMapper {
    NavigateRequest mapToNavigateRequest(NavigateRequestDto navigateRequestDto);
    NavigateResponseDto mapToNavigateResponseDto(NavigateResult navigateResult);
}
