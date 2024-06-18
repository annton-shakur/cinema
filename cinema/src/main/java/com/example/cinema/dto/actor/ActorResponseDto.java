package com.example.cinema.dto.actor;

import lombok.Data;

@Data
public class ActorResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String description;
    private String imageUrl;
}
