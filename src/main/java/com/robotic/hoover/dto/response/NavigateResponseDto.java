package com.robotic.hoover.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NavigateResponseDto {

    private List<Integer> coords;
    private int patches;
}
