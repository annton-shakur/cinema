package com.example.cinema.controller.web;

import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MovieWebController {
    private final MovieService movieService;
    private final RatingService ratingService;

    @GetMapping("/movies")
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

    @PostMapping("/movies/new")
    public String addMovie(@ModelAttribute final MovieCreateDto movieDto) {
        movieService.saveMovie(movieDto);
        return "redirect:/movies";
    }

    @GetMapping("movies/new")
    public String createMovieForm(final Model model) {
        MovieCreateDto movieDto = new MovieCreateDto();
        List<DirectorResponseDto> directors = List.of();
        model.addAttribute("movieDto", movieDto);
        return "movies-create";
    }

    @GetMapping("/movies/{id}")
    public String getMovieById(@PathVariable final Long id, final Model model) {
        MovieResponseDto movie = movieService.findById(id);
        model.addAttribute("movie", movie);
        return "movie-details";
    }

    @PostMapping("/movies/{id}/rate")
    public String rateMovie(@PathVariable final Long id,
                            @ModelAttribute final RatingCreateDto createDto,
                            final Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        ratingService.rateByMovieId(id, createDto, user.getId());
        return "redirect:/movies/" + id;
    }

}
