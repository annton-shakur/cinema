package com.example.cinema.dto.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class MovieCreateDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String title;
    @DecimalMin(value = "0", inclusive = false)
    private Integer duration;
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String description;
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String trailerUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    @NotNull
    private Long directorId;
    private List<Long> actorIds;
    private List<Long> categoryIds;
}
