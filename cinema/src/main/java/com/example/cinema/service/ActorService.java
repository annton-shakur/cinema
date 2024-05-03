package com.example.cinema.service;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {

    Page<ActorResponseDto> getAll(Pageable pageable);

    ActorResponseDto findById(Long id);

    ActorResponseDto saveActor(ActorCreateDto createDto);

    ActorResponseDto updateById(Long id, ActorUpdateDto updateDto);

    void deleteById(Long id);

    Page<ActorResponseDto> searchByName(String name, Pageable pageable);
}
