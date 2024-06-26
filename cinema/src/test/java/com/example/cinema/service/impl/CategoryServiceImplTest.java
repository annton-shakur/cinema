package com.example.cinema.service.impl;

import com.example.cinema.dto.category.CategoryCreateDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.category.CategoryUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.CategoryMapper;
import com.example.cinema.model.Category;
import com.example.cinema.repository.CategoryRepository;
import com.example.cinema.util.TestParamsInitUtil;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static Category categoryOne;
    private static Category categoryTwo;
    private static Category categoryThree;
    private static Category categoryOneWithoutId;
    private static CategoryCreateDto categoryOneCreateDto;
    private static CategoryCreateDto categoryTwoCreateDto;
    private static CategoryCreateDto categoryThreeCreateDto;
    private static CategoryResponseDto categoryOneResponseDto;
    private static CategoryResponseDto categoryTwoResponseDto;
    private static CategoryUpdateDto categoryOneUpdateDto;
    private static CategoryResponseDto updatedCategoryOneResponseDto;
    private static CategoryResponseDto categoryThreeResponseDto;
    private static Long validId;
    private static Long invalidId;
    private static String searchParam;
    private static Page<CategoryResponseDto> categoryResponseDtoPage;
    private static Page<CategoryResponseDto> categoryResponseDtoByNamePage;
    private static Page<Category> categoriesPage;
    private static Page<Category> categoriesByNamePage;
    private static Pageable pageable;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private Logger logger;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeAll
    static void beforeAll() {
        validId = 1L;
        invalidId = 100L;

        searchParam = "detect";

        pageable = PageRequest.of(0, 10);

        categoryOne = new Category();
        categoryTwo = new Category();
        categoryThree = new Category();
        categoryOneWithoutId = new Category();
        TestParamsInitUtil.initializeCategories(categoryOne, categoryTwo,
                categoryThree);
        categoryOneCreateDto = new CategoryCreateDto();
        categoryTwoCreateDto = new CategoryCreateDto();
        categoryThreeCreateDto = new CategoryCreateDto();
        categoryOneResponseDto = new CategoryResponseDto();
        categoryTwoResponseDto = new CategoryResponseDto();
        categoryThreeResponseDto = new CategoryResponseDto();
        categoryOneUpdateDto = new CategoryUpdateDto();
        updatedCategoryOneResponseDto = new CategoryResponseDto();
        TestParamsInitUtil.initializeCategoryCreateDtos(
                categoryOneCreateDto,
                categoryTwoCreateDto,
                categoryThreeCreateDto);

        TestParamsInitUtil.initializeCategoryResponseDtos(
                categoryOneResponseDto,
                categoryTwoResponseDto,
                categoryThreeResponseDto);
        List<CategoryResponseDto> categoryResponseDtoList = List.of(
                categoryOneResponseDto, categoryTwoResponseDto, categoryThreeResponseDto);

        final List<CategoryResponseDto> categoryResponseDtoByNameList = List.of(
                categoryOneResponseDto, categoryThreeResponseDto);

        List<Category> categoriesList = List.of(categoryOne, categoryTwo, categoryThree);

        List<Category> categoriesByNameList = List.of(categoryOne, categoryThree);

        categoriesPage = new PageImpl<>(
                categoriesList, pageable, categoriesList.size());

        categoriesByNamePage = new PageImpl<>(
                categoriesByNameList, pageable, categoriesByNameList.size());

        categoryResponseDtoPage = new PageImpl<>(
                categoryResponseDtoList, pageable, categoryResponseDtoList.size());

        categoryResponseDtoByNamePage = new PageImpl<>(
                categoryResponseDtoByNameList, pageable, categoryResponseDtoByNameList.size());
    }

    @Test
    @DisplayName("Save new category and return response dto")
    void saveCategory_WithValidDto_ReturnResponseDto() {
        Mockito.when(categoryMapper.toModel(categoryOneCreateDto))
                .thenReturn(categoryOneWithoutId);
        Mockito.when(categoryRepository.save(categoryOneWithoutId))
                .thenReturn(categoryOne);
        Mockito.when(categoryMapper.toDto(categoryOneWithoutId))
                .thenReturn(categoryOneResponseDto);

        CategoryResponseDto actual = categoryService.saveCategory(categoryOneCreateDto);
        CategoryResponseDto expected = categoryOneResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find category by valid id and return its dto")
    void findById_WithValidId_ReturnResponseDto() {
        Mockito.when(categoryRepository.findById(validId))
                .thenReturn(Optional.of(categoryOne));
        Mockito.when(categoryMapper.toDto(categoryOne))
                .thenReturn(categoryOneResponseDto);

        CategoryResponseDto actual = categoryService.findById(validId);
        CategoryResponseDto expected = categoryOneResponseDto;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to find a category by invalid id")
    void findById_WithInvalidId_ThrowCustomException() {
        Mockito.when(categoryRepository.findById(invalidId))
                .thenThrow(new EntityNotFoundException("Cannot find category by id " + invalidId));
        Assertions.assertThrows(
                EntityNotFoundException.class, () -> categoryService.findById(invalidId));
    }

    @Test
    @DisplayName("Get all categories and return response dto page")
    void getAllCategories_ReturnResponseDtoPage() {
        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(categoriesPage);
        Mockito.when(categoryMapper.toDto(categoryOne)).thenReturn(categoryOneResponseDto);
        Mockito.when(categoryMapper.toDto(categoryTwo)).thenReturn(categoryTwoResponseDto);
        Mockito.when(categoryMapper.toDto(categoryThree)).thenReturn(categoryThreeResponseDto);

        Page<CategoryResponseDto> actualPage = categoryService.getAll(pageable);
        Page<CategoryResponseDto> expectedPage = categoryResponseDtoPage;

        Assertions.assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
        Assertions.assertEquals(expectedPage.getContent(), actualPage.getContent());
    }

    @Test
    @DisplayName("Get all categories starting with \"detect\"")
    void searchByName_WithValidName_ReturnCategoriesDtoPage() {
        Mockito.when(categoryRepository.findByNameStartingWithIgnoreCase(searchParam, pageable))
                .thenReturn(categoriesByNamePage);
        Mockito.when(categoryMapper.toDto(categoryOne)).thenReturn(categoryOneResponseDto);
        Mockito.when(categoryMapper.toDto(categoryThree)).thenReturn(categoryThreeResponseDto);

        Page<CategoryResponseDto> actual = categoryService.searchByName(searchParam, pageable);
        Page<CategoryResponseDto> expected = categoryResponseDtoByNamePage;

        Assertions.assertEquals(expected.getTotalElements(), actual.getTotalElements());
        Assertions.assertEquals(expected.getContent(), actual.getContent());
    }

    @Test
    @DisplayName("Delete book from DB by id")
    void deleteById_WithValidId() {
        Mockito.doNothing().when(categoryRepository).deleteById(validId);
        Assertions.assertDoesNotThrow(() -> categoryService.deleteById(validId));
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(validId);
    }

    @Test
    @DisplayName("Update category with valid id and return updated response dto")
    void updateCategory_WithValidId_ReturnUpdatedResponseDto() {
        Mockito.when(categoryRepository.findById(validId)).thenReturn(Optional.of(categoryOne));
        Mockito.when(categoryRepository.save(categoryOne)).thenReturn(categoryOne);
        Mockito.when(categoryMapper.toDto(categoryOne)).thenReturn(updatedCategoryOneResponseDto);

        CategoryResponseDto actual = categoryService.updateCategory(validId, categoryOneUpdateDto);
        CategoryResponseDto expected = updatedCategoryOneResponseDto;

        Assertions.assertEquals(expected, actual);
        Mockito.verify(categoryRepository, Mockito.times(1)).findById(validId);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(categoryOne);
        Mockito.verify(categoryMapper, Mockito.times(1)).toDto(categoryOne);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to update a category by invalid id")
    void updateCategory_WithInvalidId_ThrowCustomException() {
        Mockito.when(categoryRepository.findById(invalidId))
                .thenThrow(new EntityNotFoundException("Cannot find category by id " + invalidId));
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.updateCategory(invalidId, categoryOneUpdateDto));
    }
}
