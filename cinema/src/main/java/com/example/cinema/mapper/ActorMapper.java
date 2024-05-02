package com.example.cinema.mapper;

import com.example.cinema.config.MapperConfig;
import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.model.Actor;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ActorMapper {

    ActorResponseDto toDto(Actor actor);

    Actor toModel(ActorCreateDto createDto);
}
