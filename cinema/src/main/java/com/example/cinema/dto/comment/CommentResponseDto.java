package com.example.cinema.dto.comment;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private Long movieId;
    private String content;
}
