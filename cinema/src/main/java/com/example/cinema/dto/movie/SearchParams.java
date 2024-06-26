package com.example.cinema.dto.movie;

import java.util.List;
import lombok.Data;

@Data
public class SearchParams {
    private Long directorId;
    private List<Long> actorIds;
    private List<Long> categoryIds;
    private Integer rating;
    private Integer year;
}
