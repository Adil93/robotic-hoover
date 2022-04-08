package com.robotic.hoover.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class NavigateResponseDto {

    private List<Integer> coords;
    private int patches;
}
