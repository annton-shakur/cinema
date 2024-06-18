package com.example.cinema.dto.movie;

import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.comment.CommentResponseDto;
import com.example.cinema.dto.director.DirectorResponseDto;
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
    private DirectorResponseDto director;
    private Double averageRating;
    private String imageUrl;
    private List<ActorResponseDto> actors;
    private List<CategoryResponseDto> categories;
    private List<CommentResponseDto> comments;
}
