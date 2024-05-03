package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface DirectorMapper {
    // now this mapper may seem very poor, i will fix it once i implement the movie logic

    @Mapping(target = "movieIds", expression = "java(mapMovieIds(director.getMovieList()))")
    DirectorResponseDto toDto(Director director);

    default List<Long> mapMovieIds(List<Movie> movieList) {
        return movieList.stream()
                .map(Movie::getId)
                .collect(Collectors.toList());
    }

    @Mapping(target = "movieList", ignore = true)
    Director toModel(DirectorCreateDto createDto);
}
