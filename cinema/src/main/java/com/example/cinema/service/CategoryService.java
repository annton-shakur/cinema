package com.example.cinema.service;

import com.example.cinema.dto.category.CategoryCreateDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.category.CategoryUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponseDto> searchByName(final String name, final Pageable pageable);

    Page<CategoryResponseDto> getAll(final Pageable pageable);

    CategoryResponseDto findById(final Long id);

    CategoryResponseDto saveCategory(final CategoryCreateDto createDto);

    CategoryResponseDto updateCategory(final Long id, final CategoryUpdateDto updateDto);

    void deleteById(final Long id);
}
