package com.example.cinema.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RatingCreateDto {
    @Min(1)
    @Max(5)
    private Integer rating;
}
