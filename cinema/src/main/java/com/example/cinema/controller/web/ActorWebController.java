package com.example.cinema.controller.web;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.service.ActorService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorWebController {
    private final ActorService actorService;
    private final MovieService movieService;

    @GetMapping
    public String getAllActors(final Model model, final Pageable pageable) {
        Page<ActorResponseDto> actors = actorService.getAll(pageable);
        model.addAttribute("actors", actors);
        return "actors";
    }

    @GetMapping("/new")
    public String showCreateActorForm(final Model model) {
        model.addAttribute("actorDto", new ActorCreateDto());
        return "actors-create";
    }

    @PostMapping("/new")
    public String createActor(@ModelAttribute final ActorCreateDto actorCreateDto) {
        actorService.saveActor(actorCreateDto);
        return "redirect:/actors";
    }

    @GetMapping("/{id}")
    public String getActorById(@PathVariable final Long id, final Model model) {
        ActorResponseDto actor = actorService.findById(id);
        List<MovieResponseDto> movies = movieService.findAllByActorId(id);
        model.addAttribute("actor", actor);
        model.addAttribute("movies", movies);
        return "actor-details";
    }

    @PatchMapping("/{id}")
    public String updateActorById(@PathVariable final Long id,
                                  @ModelAttribute final ActorUpdateDto actorUpdateDto) {
        actorService.updateById(id, actorUpdateDto);
        return "redirect:/actors/" + id;
    }
}
