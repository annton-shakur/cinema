package com.example.cinema.service;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DirectorService {
    Page<DirectorResponseDto> searchByName(final String name, final Pageable pageable);

    Page<DirectorResponseDto> findAll(final Pageable pageable);

    DirectorResponseDto findById(final Long id);

    DirectorResponseDto saveDirector(final DirectorCreateDto createDto);

    DirectorResponseDto updateDirector(final Long id, final DirectorUpdateDto updateDto);

    void deleteById(final Long id);
}
