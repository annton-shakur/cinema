package com.example.cinema.service;

import com.example.cinema.dto.comment.CommentCreateDto;
import com.example.cinema.dto.comment.CommentResponseDto;
import com.example.cinema.dto.comment.CommentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentResponseDto> findAll(final Pageable pageable);

    CommentResponseDto findById(final Long id);

    Page<CommentResponseDto> findByMovieId(final Pageable pageable, final Long movieId);

    CommentResponseDto updateById(final Long id, final CommentUpdateDto updateDto);

    void deleteComment(final Long id);

    CommentResponseDto saveComment(final CommentCreateDto createDto);
}
