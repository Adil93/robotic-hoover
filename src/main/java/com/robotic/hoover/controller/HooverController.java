package com.robotic.hoover.controller;

import com.robotic.hoover.errorHandling.exception.UnProcessableEntityException;
import com.robotic.hoover.dto.request.NavigateRequestDto;
import com.robotic.hoover.dto.response.NavigateResponseDto;
import com.robotic.hoover.service.IHooverService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("hoover")
@AllArgsConstructor
@Slf4j
public class HooverController {

    private IHooverService hooverService;

    @PostMapping(value = "navigate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NavigateResponseDto> navigate(@RequestBody @Valid NavigateRequestDto navigateRequestDto) throws UnProcessableEntityException {
        log.info("Begin robotic hoover navigate {}", navigateRequestDto.toString());
        return ResponseEntity.ok(hooverService.navigate(navigateRequestDto));
    }
}
