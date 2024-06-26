package com.example.cinema.dto.actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorResponseDto {
    private Long id;
    private String name;
    private Integer age;
    private String description;
    private String imageUrl;
}
