package com.example.cinema.controller;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.service.ActorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final Logger logger;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<ActorResponseDto> getAllActors(final Pageable pageable,
                                               @RequestParam(required = false) final String name
    ) {
        if (name != null) {
            logger.info("getAll method was called with the next name: {}", name);
            return actorService.searchByName(name, pageable);
        } else {
            logger.info("Default getAll method was called");
            return actorService.getAll(pageable);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ActorResponseDto getById(@PathVariable final Long id) {
        logger.info("getById method was called with the next id: {}", id);
        return actorService.findById(id);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping
    public ActorResponseDto saveActor(@RequestBody @Valid final ActorCreateDto createDto) {
        logger.info("saveActor method was called with the next dto: {}", createDto);
        return actorService.saveActor(createDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PatchMapping("/{id}")
    public ActorResponseDto updateById(@PathVariable final Long id,
                                       @RequestBody final ActorUpdateDto updateDto
    ) {
        logger.info("updateById method was called for the next id and dto: {}, {}", id, updateDto);
        return actorService.updateById(id, updateDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final Long id) {
        logger.info("delete method was called for the next id: {}", id);
        actorService.deleteById(id);
    }
}
