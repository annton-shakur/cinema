package com.example.cinema.service.impl;

import com.example.cinema.dto.category.CategoryCreateDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.category.CategoryUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.CategoryMapper;
import com.example.cinema.model.Category;
import com.example.cinema.repository.CategoryRepository;
import com.example.cinema.service.CategoryService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final String CANNOT_FIND_CATEGORY_BY_ID_MSG = "Cannot find category by id: ";
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final Logger logger;

    @Override
    public Page<CategoryResponseDto> searchByName(final String name, final Pageable pageable) {
        logger.info("[Service]: Searching categories by name: {}", name);
        Page<Category> categoriesFromDb = categoryRepository
                .findByNameStartingWithIgnoreCase(name, pageable);
        return categoriesFromDb.map(categoryMapper::toDto);
    }

    @Override
    public Page<CategoryResponseDto> getAll(final Pageable pageable) {
        logger.info("[Service]: Getting all categories");
        Page<Category> categoriesFromDb = categoryRepository.findAll(pageable);
        return categoriesFromDb.map(categoryMapper::toDto);
    }

    @Override
    public CategoryResponseDto findById(final Long id) {
        logger.info("[Service]: Finding category by id: {}", id);
        Category categoryFromDb = categoryRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_CATEGORY_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_CATEGORY_BY_ID_MSG + id);
                }
        );
        return categoryMapper.toDto(categoryFromDb);
    }

    @Override
    public CategoryResponseDto saveCategory(final CategoryCreateDto createDto) {
        logger.info("[Service]: Saving category: {}", createDto);
        Category category = categoryMapper.toModel(createDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryResponseDto updateCategory(final Long id, final CategoryUpdateDto updateDto) {
        logger.info("[Service]: Updating category with id {}: {}", id, updateDto);
        Category categoryFromDb = categoryRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_CATEGORY_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_CATEGORY_BY_ID_MSG + id);
                }
        );
        setUpdatedFields(categoryFromDb, updateDto);
        categoryRepository.save(categoryFromDb);
        return categoryMapper.toDto(categoryFromDb);
    }

    @Override
    public void deleteById(final Long id) {
        logger.info("[Service]: Deleting category with id: {}", id);
        categoryRepository.deleteById(id);
    }

    private void setUpdatedFields(final Category categoryFromDb,
                                  final CategoryUpdateDto updateDto
    ) {
        Optional.ofNullable(updateDto.getName()).ifPresent(categoryFromDb::setName);
    }
}
