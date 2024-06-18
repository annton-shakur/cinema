package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.model.Director;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface DirectorMapper {

    DirectorResponseDto toDto(Director director);

    Director toModel(DirectorCreateDto createDto);
}
