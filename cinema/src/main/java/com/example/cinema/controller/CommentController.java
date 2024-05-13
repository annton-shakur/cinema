package com.example.cinema.controller;

import com.example.cinema.dto.comment.CommentCreateDto;
import com.example.cinema.dto.comment.CommentResponseDto;
import com.example.cinema.dto.comment.CommentUpdateDto;
import com.example.cinema.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final Logger logger;

    @GetMapping
    Page<CommentResponseDto> getAllComments(final Pageable pageable,
                                            @RequestParam(required = false) final Long movieId
    ) {
        if (movieId != null) {
            logger.info("getByMovieId method was called with the next movieId: {}", movieId);
            return commentService.findByMovieId(pageable, movieId);
        } else {
            logger.info("getAllCategories method was called");
            return commentService.findAll(pageable);
        }
    }

    @GetMapping("/{id}")
    CommentResponseDto getById(@PathVariable final Long id) {
        logger.info("getById method was called with the next id: {}", id);
        return commentService.findById(id);
    }

    @PostMapping CommentResponseDto addComment(@RequestBody final CommentCreateDto createDto) {
        logger.info("addComment method was called with the next dto: {}", createDto);
        return commentService.saveComment(createDto);
    }

    @PutMapping("/{id}")
    CommentResponseDto updateComment(@PathVariable final Long id,
                                     @RequestBody final CommentUpdateDto updateDto
    ) {
        logger.info("updateById method was called for the next id and dto: {}, {}", id, updateDto);
        return commentService.updateById(id, updateDto);
    }

    @DeleteMapping("/{id}")
    void deleteComment(@PathVariable final Long id) {
        logger.info("delete method was called for the next id: {}", id);
        commentService.deleteComment(id);
    }
}