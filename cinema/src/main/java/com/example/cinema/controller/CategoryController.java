package com.example.cinema.controller;

import com.example.cinema.dto.category.CategoryCreateDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.category.CategoryUpdateDto;
import com.example.cinema.service.CategoryService;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final Logger logger;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public Page<CategoryResponseDto> getAllCategories(
            final Pageable pageable,
            @RequestParam(required = false) final String name
    ) {
        if (name != null) {
            logger.info("getAllCategories method was called with the next name: {}", name);
            return categoryService.searchByName(name, pageable);
        } else {
            logger.info("Default getAllCategories method was called");
            return categoryService.getAll(pageable);
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable final Long id) {
        logger.info("getById method was called with the next id: {}", id);
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PostMapping
    public CategoryResponseDto saveCategory(@RequestBody final CategoryCreateDto createDto) {
        logger.info("saveCategory method was called with the next dto: {}", createDto);
        return categoryService.saveCategory(createDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PatchMapping("/{id}")
    public CategoryResponseDto updateById(@PathVariable final Long id,
                                              @RequestBody final CategoryUpdateDto updateDto) {
        logger.info("updateById method was called for the next id and dto: {}, {}", id, updateDto);
        return categoryService.updateCategory(id, updateDto);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final Long id) {
        logger.info("delete method was called for the next id: {}", id);
        categoryService.deleteById(id);
    }
}
