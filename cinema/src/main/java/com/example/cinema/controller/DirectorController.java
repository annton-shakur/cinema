package com.example.cinema.controller;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.service.DirectorService;
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
@RequestMapping("/api/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping
    public Page<DirectorResponseDto> getAllDirectors(Pageable pageable,
                                             @RequestParam(required = false) String name
    ) {
        if (name != null) {
            return directorService.searchByName(name, pageable);
        } else {
            return directorService.findAll(pageable);
        }
    }

    @GetMapping("/{id}")
    public DirectorResponseDto getById(@PathVariable Long id) {
        return directorService.findById(id);
    }

    @PostMapping
    public DirectorResponseDto saveDirector(@RequestBody @Valid DirectorCreateDto createDto) {
        return directorService.saveDirector(createDto);
    }

    @PatchMapping("/{id}")
    public DirectorResponseDto updateDirector(@PathVariable Long id,
                                              @RequestBody DirectorUpdateDto updateDto
    ) {
        return directorService.updateDirector(id, updateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        directorService.deleteById(id);
    }
}
