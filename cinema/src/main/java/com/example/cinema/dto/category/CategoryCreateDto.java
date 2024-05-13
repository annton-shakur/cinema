package com.example.cinema.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String name;
}
