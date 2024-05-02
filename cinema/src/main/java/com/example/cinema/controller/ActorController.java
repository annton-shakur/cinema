package com.example.cinema.controller;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/actors")
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @GetMapping
    public Page<ActorResponseDto> getAllActors(Pageable pageable,
                                               @RequestParam(required = false) String name
    ) {
        if (name != null) {
            return actorService.searchByName(name, pageable);
        } else {
            return actorService.getAll(pageable);
        }
    }

    @GetMapping("/{id}")
    public ActorResponseDto getById(@PathVariable Long id) {
        return actorService.findById(id);
    }

    @PostMapping
    public ActorResponseDto saveActor(@RequestBody @Valid ActorCreateDto createDto) {
        return actorService.saveActor(createDto);
    }

    @PatchMapping("/{id}")
    public ActorResponseDto updateById(@PathVariable Long id,
                                       @RequestBody ActorUpdateDto updateDto
    ) {
        return actorService.updateById(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        actorService.deleteById(id);
    }
}
