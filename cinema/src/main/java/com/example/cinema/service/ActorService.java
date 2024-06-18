package com.example.cinema.service;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActorService {

    Page<ActorResponseDto> getAll(final Pageable pageable);

    ActorResponseDto findById(final Long id);

    ActorResponseDto saveActor(final ActorCreateDto createDto);

    ActorResponseDto updateById(final Long id, final ActorUpdateDto updateDto);

    void deleteById(final Long id);

    Page<ActorResponseDto> searchByName(final String name, final Pageable pageable);

    List<ActorResponseDto> searchByName(final String name);
}
