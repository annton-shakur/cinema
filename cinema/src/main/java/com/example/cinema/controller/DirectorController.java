package com.example.cinema.controller;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.service.DirectorService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;
    private final Logger logger;

    @GetMapping
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

    @GetMapping("/{id}")
    public DirectorResponseDto getById(@PathVariable final Long id) {
        logger.info("getById method was called with the next id: {}", id);
        return directorService.findById(id);
    }

    @PostMapping
    public DirectorResponseDto saveDirector(@RequestBody @Valid final DirectorCreateDto createDto) {
        logger.info("saveDirector method was called with the next dto: {}", createDto);
        return directorService.saveDirector(createDto);
    }

    @PatchMapping("/{id}")
    public DirectorResponseDto updateDirector(@PathVariable final Long id,
                                              @RequestBody final DirectorUpdateDto updateDto
    ) {
        logger.info("updateById method was called for the next id and dto: {}, {}", id, updateDto);
        return directorService.updateDirector(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final Long id) {
        logger.info("delete method was called for the next id: {}", id);
        directorService.deleteById(id);
    }
}
