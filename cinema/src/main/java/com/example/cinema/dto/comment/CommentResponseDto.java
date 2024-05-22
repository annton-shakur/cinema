package com.example.cinema.dto.comment;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private Long movieId;
    private LocalDateTime creationTime;
    private Long userId;
    private String content;
}
