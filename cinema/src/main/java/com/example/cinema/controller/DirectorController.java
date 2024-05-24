package com.example.cinema.controller;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Directors management",
        description = "Endpoints for viewing, adding and updating directors")
@RestController
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;
    private final Logger logger;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get all directors",
            description = "Return a page of directors (optionally filtered by name)")
    public Page<DirectorResponseDto> getAllDirectors(final Pageable pageable,
                                             @RequestParam(required = false) final String name
    ) {
        if (name != null) {
            logger.info("getAllDirectors method was called with the next name: {}", name);
            return directorService.searchByName(name, pageable);
        } else {
            logger.info("Default getAllDirectors method was called");
            return directorService.findAll(pageable);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get director by id",
            description = "Return a director DTO mapped from entity found in DB by id")
    public DirectorResponseDto getById(@PathVariable final Long id) {
        logger.info("getById method was called with the next id: {}", id);
        return directorService.findById(id);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping
    @Operation(summary = "Add a new director",
            description = "Return a DTO of a newly-saved director")
    public DirectorResponseDto saveDirector(@RequestBody @Valid final DirectorCreateDto createDto) {
        logger.info("saveDirector method was called with the next dto: {}", createDto);
        return directorService.saveDirector(createDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update director by id",
            description = "Update the specified director fields and return its new version")
    public DirectorResponseDto updateDirector(@PathVariable final Long id,
                                              @RequestBody final DirectorUpdateDto updateDto
    ) {
        logger.info("updateById method was called for the next id and dto: {}, {}", id, updateDto);
        return directorService.updateDirector(id, updateDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete director by id",
            description = "(soft) delete director from DB by id")
    public void deleteById(@PathVariable final Long id) {
        logger.info("delete method was called for the next id: {}", id);
        directorService.deleteById(id);
    }
}
