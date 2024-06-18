package com.example.cinema.service;

import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieSearchParameters;
import com.example.cinema.dto.movie.MovieUpdateDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    Page<MovieResponseDto> searchByTitle(final Pageable pageable, final String title);

    Page<MovieResponseDto> getAll(final Pageable pageable);

    MovieResponseDto findById(final Long id);

    MovieResponseDto saveMovie(final MovieCreateDto createDto);

    MovieResponseDto updateMovie(final Long id, final MovieUpdateDto updateDto);

    void deleteById(final Long id);

    Page<MovieResponseDto> searchMovies(final MovieSearchParameters searchDto,
                                        final Pageable pageable);

    List<MovieResponseDto> findAllByActorId(final Long id);

    List<MovieResponseDto> findAllByDirectorId(final Long id);
}
