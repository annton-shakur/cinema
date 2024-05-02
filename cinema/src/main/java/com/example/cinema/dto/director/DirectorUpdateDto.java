package com.example.cinema.dto.director;

import java.util.List;
import lombok.Data;

@Data
public class DirectorUpdateDto {
    private String name;
    private String description;
    private List<Long> movieIds;
}
