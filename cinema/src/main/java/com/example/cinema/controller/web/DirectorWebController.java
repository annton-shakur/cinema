package com.example.cinema.controller.web;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.service.DirectorService;
import com.example.cinema.service.MovieService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorWebController {
    private final MovieService movieService;
    private final DirectorService directorService;

    @GetMapping
    public String getAllDirectors(final Model model,
                                  final Pageable pageable,
                                  final @RequestParam(required = false) String name) {
        Page<DirectorResponseDto> directorPage;

        if (name != null && !name.isEmpty()) {
            directorPage = directorService.searchByName(name, pageable);
        } else {
            directorPage = directorService.findAll(pageable);
        }

        model.addAttribute("directors", directorPage);
        return "directors";
    }

    @GetMapping("/{id}")
    public String getDirectorDetails(final @PathVariable Long id, final Model model) {
        DirectorResponseDto director = directorService.findById(id);
        List<MovieResponseDto> movies = movieService.findAllByDirectorId(id);
        model.addAttribute("director", director);
        model.addAttribute("movies", movies);
        return "director-details";
    }

    @GetMapping("/new")
    public String showCreateActorForm(final Model model) {
        model.addAttribute("directorDto", new DirectorCreateDto());
        return "directors-create";
    }

    @PostMapping("/new")
    public String createActor(@ModelAttribute final DirectorCreateDto directorCreateDto) {
        directorService.saveDirector(directorCreateDto);
        return "redirect:/directors";
    }

    @PatchMapping("/{id}")
    public String updateActorById(@PathVariable final Long id,
                                  @ModelAttribute final DirectorUpdateDto directorUpdateDto) {
        directorService.updateDirector(id, directorUpdateDto);
        return "redirect:/directors/" + id;
    }
}
