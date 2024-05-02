package com.example.cinema.dto.director;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class DirectorCreateDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String name;
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String description;
    private List<Long> movieIds;
}
