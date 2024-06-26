package com.example.cinema.controller;

import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.dto.movie.SearchParams;
import com.example.cinema.dto.rating.RatingCreateDto;
import com.example.cinema.model.User;
import com.example.cinema.service.MovieService;
import com.example.cinema.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Movies management",
        description = "Endpoints for viewing, adding and updating movies")
@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final Logger logger;
    private final RatingService ratingService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get all movies",
            description = "Return a page of movies (optionally filtered by name)")
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get movie by id",
            description = "Return a movie DTO mapped from entity found in DB by id")
    public MovieResponseDto getById(@PathVariable final Long id) {
        logger.info("getById method was called with the next id: {}", id);
        return movieService.findById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/search")
    @Operation(summary = "Search movies by different params",
            description = "Search movies by dynamic params using criteria")
    Page<MovieResponseDto> searchMovies(@RequestBody final SearchParams searchParams,
                                        final Pageable pageable
    ) {
        return movieService.searchMovies(searchParams, pageable);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping
    @Operation(summary = "Add a new movie",
            description = "Return a DTO of a newly-saved movie")
    public MovieResponseDto saveMovie(@RequestBody final MovieCreateDto createDto) {
        logger.info("saveMovie method was called with the next dto: {}", createDto);
        return movieService.saveMovie(createDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update movie by id",
            description = "Update the specified movie fields and return its new version")
    public MovieResponseDto updateById(@PathVariable final Long id,
                                       @RequestBody final MovieUpdateDto updateDto) {
        logger.info("updateById method was called for the next id and dto: {}, {}", id, updateDto);
        return movieService.updateMovie(id, updateDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete movie by id",
            description = "(soft) delete movie from DB by id")
    public void deleteById(@PathVariable final Long id) {
        logger.info("delete method was called for the next id: {}", id);
        movieService.deleteById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/rate")
    @Operation(summary = "Add movie rating",
            description = "Rate movie from 1 to 5 and gets its dto as a response")
    public MovieResponseDto rateMovie(@PathVariable final Long id,
                                      @RequestBody @Valid final RatingCreateDto createDto,
                                      final Authentication authentication
    ) {
        logger.info("rate movie method was called for the next id: {}", id);
        User user = (User) authentication.getPrincipal();
        return ratingService.rateByMovieId(id, createDto, user.getId());
    }
}
