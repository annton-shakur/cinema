package com.example.cinema.controller;

import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieSearchParameters;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final Logger logger;

    @GetMapping
    public Page<MovieResponseDto> getAllMovies(final Pageable pageable,
                                               @RequestParam(required = false) final String title
    ) {
        if (title != null) {
            logger.info("getAllMovies method was called with the next title: {}", title);
            return movieService.searchByTitle(pageable, title);
        } else {
            logger.info("Default getAllMovies method was called");
            return movieService.getAll(pageable);
        }
    }

    @GetMapping("/{id}")
    public MovieResponseDto getById(@PathVariable final Long id) {
        logger.info("getById method was called with the next id: {}", id);
        return movieService.findById(id);
    }

    @GetMapping("/search")
    Page<MovieResponseDto> searchMovies(final MovieSearchParameters searchDto,
                                        final Pageable pageable
    ) {
        return movieService.searchMovies(searchDto, pageable);
    }

    @PostMapping
    public MovieResponseDto saveMovie(@RequestBody final MovieCreateDto createDto) {
        logger.info("saveMovie method was called with the next dto: {}", createDto);
        return movieService.saveMovie(createDto);
    }

    @PatchMapping("/{id}")
    public MovieResponseDto updateById(@PathVariable final Long id,
                                       @RequestBody final MovieUpdateDto updateDto) {
        logger.info("updateById method was called for the next id and dto: {}, {}", id, updateDto);
        return movieService.updateMovie(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final Long id) {
        logger.info("delete method was called for the next id: {}", id);
        movieService.deleteById(id);
    }
}
