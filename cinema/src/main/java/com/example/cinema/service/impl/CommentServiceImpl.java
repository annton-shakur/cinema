package com.example.cinema.service.impl;

import com.example.cinema.dto.comment.CommentCreateDto;
import com.example.cinema.dto.comment.CommentResponseDto;
import com.example.cinema.dto.comment.CommentUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.CommentMapper;
import com.example.cinema.model.Comment;
import com.example.cinema.repository.CommentRepository;
import com.example.cinema.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private static final String CANNOT_FIND_COMMENT_BY_ID_MSG = "Cannot find comment by id:";
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final Logger logger;

    @Override
    public Page<CommentResponseDto> findAll(final Pageable pageable) {
        logger.info("[Service]: Finding all comments");
        Page<Comment> commentsFromDb = commentRepository.findAll(pageable);
        return commentsFromDb.map(commentMapper::toDto);
    }

    @Override
    public CommentResponseDto findById(final Long id) {
        logger.info("[Service]: Finding comment by id: {}", id);
        Comment commentFromDb = commentRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_COMMENT_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_COMMENT_BY_ID_MSG + id);
                }
        );
        return commentMapper.toDto(commentFromDb);
    }

    @Override
    public Page<CommentResponseDto> findByMovieId(final Pageable pageable, final Long movieId) {
        logger.info("[Service]: Finding comments by movie id: {}", movieId);
        Page<Comment> commentsFromDb = commentRepository.findByMovieId(pageable, movieId);
        return commentsFromDb.map(commentMapper::toDto);
    }

    @Override
    public CommentResponseDto updateById(final Long id, final CommentUpdateDto updateDto) {
        logger.info("[Service]: Updating comment with id {}: {}", id, updateDto);
        Comment commentFromDb = commentRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_COMMENT_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_COMMENT_BY_ID_MSG + id);
                }
        );
        commentFromDb.setContent(updateDto.getContent());
        commentRepository.save(commentFromDb);
        return commentMapper.toDto(commentFromDb);
    }

    @Override
    public void deleteComment(final Long id) {
        logger.info("[Service]: Deleting comment by id: {}", id);
        commentRepository.deleteById(id);
    }

    @Override
    public CommentResponseDto saveComment(final CommentCreateDto createDto) {
        logger.info("[Service]: Saving new comment: {}", createDto);
        Comment comment = commentMapper.toModel(createDto);
        commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }
}
