package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.comment.CommentCreateDto;
import com.example.cinema.dto.comment.CommentResponseDto;
import com.example.cinema.model.Comment;
import com.example.cinema.model.Movie;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "movieId", source = "movie.id")
    CommentResponseDto toDto(Comment comment);

    @Mapping(target = "movie", ignore = true)
    Comment toModel(CommentCreateDto createDto);

    @AfterMapping
    default void setMovie(@MappingTarget Comment comment, CommentCreateDto createDto) {
        comment.setMovie(new Movie(createDto.getMovieId()));
    }
}
