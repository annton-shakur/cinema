package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import java.util.Optional;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface DirectorMapper {

    @Mapping(target = "movieIds", ignore = true)
    DirectorResponseDto toDto(Director director);

    @Mapping(target = "movieList", ignore = true)
    Director toModel(DirectorCreateDto createDto);

    @AfterMapping
    default void setMovies(@MappingTarget DirectorResponseDto responseDto,
                           Director director
    ) {
        responseDto.setMovieIds(director.getMovieList()
                .stream()
                .map(Movie::getId)
                .toList());
    }

    @AfterMapping
    default void setMovies(@MappingTarget Director director,
                           DirectorCreateDto createDto
    ) {
        Optional.ofNullable(createDto.getMovieIds()).ifPresent(
                ids -> director.setMovieList(ids.stream().map(Movie::new).toList())
        );
    }
}
