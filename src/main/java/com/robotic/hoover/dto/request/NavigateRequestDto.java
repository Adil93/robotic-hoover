package com.robotic.hoover.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class NavigateRequestDto {

    @NotEmpty
    @Size(min = 2, max = 2, message = "Expect 2 entries")
    private List<Integer> roomSize;

    @NotEmpty
    @Size(min = 2, max = 2, message = "Expect 2 entries")
    private List<Integer> coords;

    @Size(min = 0)
    private List<List<Integer>> patches;

    @NotBlank
    @Pattern(regexp = "^[N,S,E,W]*$", message = "Invalid instructions")
    private String instructions;

}
