package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.model.Actor;
import com.example.cinema.model.Category;
import com.example.cinema.model.Comment;
import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import java.util.Optional;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface MovieMapper {

    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "director", ignore = true)
    Movie toModel(MovieCreateDto createDto);

    @Mapping(target = "categoryIds", ignore = true)
    @Mapping(target = "actorIds", ignore = true)
    @Mapping(target = "commentIds", ignore = true)
    @Mapping(target = "directorId", source = "director.id")
    MovieResponseDto toDto(Movie movie);

    @AfterMapping
    default void setCategories(@MappingTarget Movie movie, MovieCreateDto createDto) {
        movie.setCategories(createDto.getCategoryIds()
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
    }

    @AfterMapping
    default void setCategories(@MappingTarget MovieResponseDto responseDto, Movie movie) {
        responseDto.setCategoryIds(movie.getCategories()
                .stream()
                .map(Category::getId)
                .toList());
    }

    @AfterMapping
    default void setActors(@MappingTarget Movie movie, MovieCreateDto createDto) {
        movie.setActors(createDto.getActorIds()
                .stream()
                .map(Actor::new)
                .collect(Collectors.toSet()));
    }

    @AfterMapping
    default void setActors(@MappingTarget MovieResponseDto responseDto, Movie movie) {
        responseDto.setActorIds(movie.getActors()
                .stream()
                .map(Actor::getId)
                .toList());
    }

    @AfterMapping
    default void setComments(@MappingTarget MovieResponseDto responseDto, Movie movie) {
        Optional.ofNullable(movie.getComments()).ifPresent(
                (comments) -> responseDto.setCommentIds(comments
                        .stream()
                        .map(Comment::getId)
                        .toList())
        );
    }

    @AfterMapping
    default void setDirector(@MappingTarget Movie movie, MovieCreateDto createDto) {
        movie.setDirector(new Director(createDto.getDirectorId()));
    }
}
