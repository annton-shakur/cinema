package com.example.cinema.controller.web;

import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.dto.movie.SearchParams;
import com.example.cinema.dto.rating.RatingCreateDto;
import com.example.cinema.model.User;
import com.example.cinema.service.MovieService;
import com.example.cinema.service.RatingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieWebController {
    private final MovieService movieService;
    private final RatingService ratingService;

    @GetMapping
    public String getAllMovies(final Model model,
                               final Pageable pageable,
                               final @RequestParam(required = false) String title) {
        Page<MovieResponseDto> movies;
        if (title == null || title.isEmpty()) {
            movies = movieService.getAll(pageable);
        } else {
            movies = movieService.searchByTitle(pageable, title);
            model.addAttribute("searchQuery", title);
        }
        model.addAttribute("movies", movies);
        model.addAttribute("movieDto", new MovieCreateDto());
        return "movies";
    }

    @PostMapping("/new")
    public String addMovie(@ModelAttribute final MovieCreateDto movieDto) {
        movieService.saveMovie(movieDto);
        return "redirect:/movies";
    }

    @GetMapping("new")
    public String createMovieForm(final Model model) {
        MovieCreateDto movieDto = new MovieCreateDto();
        List<DirectorResponseDto> directors = List.of();
        model.addAttribute("movieDto", movieDto);
        return "movies-create";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable final Long id, final Model model) {
        MovieResponseDto movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "movie-details";
    }

    @PostMapping("/{id}/rate")
    public String rateMovie(@PathVariable final Long id,
                            @ModelAttribute final RatingCreateDto createDto,
                            final Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        ratingService.rateByMovieId(id, createDto, user.getId());
        return "redirect:/movies/" + id;
    }

    @PatchMapping("/{id}")
    public String updateActorById(@PathVariable final Long id,
                                  @ModelAttribute final MovieUpdateDto movieUpdateDto) {
        movieService.updateMovie(id, movieUpdateDto);
        return "redirect:/movies/" + id;
    }

    @GetMapping("/search")
    public String searchMovies(
            final SearchParams searchParams,
            final Pageable pageable,
            final Model model) {

        Page<MovieResponseDto> movies = movieService.searchMovies(searchParams, pageable);

        model.addAttribute("movies", movies);
        return "movies";
    }
}
