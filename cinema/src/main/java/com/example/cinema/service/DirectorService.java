package com.example.cinema.service;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DirectorService {
    Page<DirectorResponseDto> searchByName(String name, Pageable pageable);

    Page<DirectorResponseDto> findAll(Pageable pageable);

    DirectorResponseDto findById(Long id);

    DirectorResponseDto saveDirector(DirectorCreateDto createDto);

    DirectorResponseDto updateDirector(Long id, DirectorUpdateDto updateDto);

    void deleteById(Long id);
}
