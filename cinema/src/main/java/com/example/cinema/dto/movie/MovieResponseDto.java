package com.example.cinema.dto.movie;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class MovieResponseDto {
    private Long id;
    private String title;
    private Integer duration;
    private String description;
    private String trailerUrl;
    private LocalDate releaseDate;
    private Long directorId;
    private Double averageRating;
    private List<Long> actorIds;
    private List<Long> categoryIds;
    private List<Long> commentIds;
}
