package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.model.Actor;
import com.example.cinema.model.Category;
import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = {
        CommentMapper.class, CategoryMapper.class, ActorMapper.class, DirectorMapper.class
})
public interface MovieMapper {

    @Mapping(target = "actors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "director", ignore = true)
    Movie toModel(MovieCreateDto createDto);

    MovieResponseDto toDto(Movie movie);

    @AfterMapping
    default void setCategories(@MappingTarget Movie movie, MovieCreateDto createDto) {
        movie.setCategories(createDto.getCategoryIds()
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
    }

    @AfterMapping
    default void setActors(@MappingTarget Movie movie, MovieCreateDto createDto) {
        movie.setActors(createDto.getActorIds()
                .stream()
                .map(Actor::new)
                .collect(Collectors.toSet()));
    }

    @AfterMapping
    default void setDirector(@MappingTarget Movie movie, MovieCreateDto createDto) {
        movie.setDirector(new Director(createDto.getDirectorId()));
    }
}
