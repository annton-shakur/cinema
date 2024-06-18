package com.example.cinema.dto.actor;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActorCreateDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String name;
    @DecimalMin(value = "0", inclusive = false)
    private Integer age;
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String description;
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String imageUrl;
}
