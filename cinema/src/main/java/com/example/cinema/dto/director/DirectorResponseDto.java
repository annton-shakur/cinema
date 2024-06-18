package com.example.cinema.dto.director;

import lombok.Data;

@Data
public class DirectorResponseDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
}
