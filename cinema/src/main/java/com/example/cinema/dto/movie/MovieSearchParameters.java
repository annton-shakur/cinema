package com.example.cinema.dto.movie;

import lombok.Data;

@Data
public class MovieSearchParameters {
    private String[] director;
    private String[] actors;
    private String[] categories;
    private String[] rating;
    private String[] years;
}
