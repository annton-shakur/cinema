package com.example.cinema.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentCreateDto {
    private static final String CANNOT_BE_NULL_MSG = "cannot be null or blank";
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private Long movieId;
    @NotBlank(message = CANNOT_BE_NULL_MSG)
    private String content;
}
