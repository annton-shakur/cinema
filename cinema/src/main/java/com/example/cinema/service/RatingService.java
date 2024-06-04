package com.example.cinema.service;

import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.rating.RatingCreateDto;

public interface RatingService {

    MovieResponseDto rateByMovieId(final Long movieId,
                                   final RatingCreateDto createDto,
                                   final Long userId);
}
