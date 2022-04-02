package com.robotic.hoover.service;

import com.robotic.hoover.entity.NavigateRequest;
import com.robotic.hoover.entity.NavigateResult;
import com.robotic.hoover.errorHandling.exception.UnProcessableEntityException;
import com.robotic.hoover.dto.request.NavigateRequestDto;
import com.robotic.hoover.dto.response.NavigateResponseDto;

public interface IHooverService {
    NavigateResponseDto navigate(NavigateRequestDto request) throws UnProcessableEntityException;
    NavigateResult navigate(NavigateRequest request);
}
