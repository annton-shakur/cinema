package com.example.cinema.dto.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class MovieUpdateDto {
    private String title;
    private Integer duration;
    private String description;
    private String trailerUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private Long directorId;
    private List<Long> actorIds;
    private List<Long> categoryIds;
}
