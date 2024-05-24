package com.example.cinema.service.impl;

import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.DirectorMapper;
import com.example.cinema.model.Director;
import com.example.cinema.repository.DirectorRepository;
import com.example.cinema.uitl.TestParamsInitUtil;
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
class DirectorServiceImplTest {

    private static Director directorTarantino;
    private static Director directorNolan;
    private static Director directorTarantinoWithoutId;
    private static DirectorCreateDto directorTarantinoCreateDto;
    private static DirectorResponseDto directorTarantinoResponseDto;
    private static DirectorResponseDto directorNolanResponseDto;
    private static DirectorUpdateDto directorTarantinoUpdateDto;
    private static DirectorResponseDto updatedDirectorTarantinoResponseDto;
    private static Long validId;
    private static Long invalidId;
    private static String searchParam;
    private static Page<DirectorResponseDto> directorResponseDtoPage;
    private static Page<DirectorResponseDto> directorResponseDtoByNamePage;
    private static Page<Director> directorsPage;
    private static Page<Director> directorsByNamePage;
    private static Pageable pageable;

    @Mock
    private DirectorRepository directorRepository;
    @Mock
    private DirectorMapper directorMapper;
    @Mock
    private Logger logger;
    @InjectMocks
    private DirectorServiceImpl directorService;

    @BeforeAll
    static void beforeAll() {
        validId = 1L;
        invalidId = 100L;
        searchParam = "quentin";
        pageable = PageRequest.of(0, 10);

        directorTarantino = new Director();
        directorNolan = new Director();
        directorTarantinoWithoutId = new Director();
        TestParamsInitUtil.initializeDirectorModels(directorTarantino,
                directorNolan, directorTarantinoWithoutId);

        directorTarantinoCreateDto = new DirectorCreateDto();
        directorTarantinoResponseDto = new DirectorResponseDto();
        directorNolanResponseDto = new DirectorResponseDto();
        directorTarantinoUpdateDto = new DirectorUpdateDto();
        updatedDirectorTarantinoResponseDto = new DirectorResponseDto();
        TestParamsInitUtil.initializeDirectorDtos(directorTarantinoCreateDto,
                directorTarantinoResponseDto, directorNolanResponseDto,
                directorTarantinoUpdateDto, updatedDirectorTarantinoResponseDto);

        List<DirectorResponseDto> directorResponseDtoList = List.of(
                directorTarantinoResponseDto, directorNolanResponseDto);
        final List<DirectorResponseDto> directorResponseDtoByNameList = List.of(
                directorTarantinoResponseDto);
        List<Director> directorsList = List.of(directorTarantino, directorNolan);
        List<Director> directorsByNameList = List.of(directorTarantino);

        directorsPage = new PageImpl<>(
                directorsList, pageable, directorsList.size());
        directorsByNamePage = new PageImpl<>(
                directorsByNameList, pageable, directorsByNameList.size());
        directorResponseDtoPage = new PageImpl<>(
                directorResponseDtoList, pageable, directorResponseDtoList.size());
        directorResponseDtoByNamePage = new PageImpl<>(
                directorResponseDtoByNameList, pageable, directorResponseDtoByNameList.size());
    }

    @Test
    @DisplayName("Save new director and return response dto")
    void saveDirector_WithValidDto_ReturnResponseDto() {
        Mockito.when(directorMapper.toModel(directorTarantinoCreateDto))
                .thenReturn(directorTarantinoWithoutId);
        Mockito.when(directorRepository.save(directorTarantinoWithoutId))
                .thenReturn(directorTarantino);
        Mockito.when(directorMapper.toDto(directorTarantinoWithoutId))
                .thenReturn(directorTarantinoResponseDto);

        DirectorResponseDto actual = directorService.saveDirector(directorTarantinoCreateDto);
        DirectorResponseDto expected = directorTarantinoResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find director by valid id and return its dto")
    void findById_WithValidId_ReturnResponseDto() {
        Mockito.when(directorRepository.findById(validId))
                .thenReturn(Optional.of(directorTarantino));
        Mockito.when(directorMapper.toDto(directorTarantino))
                .thenReturn(directorTarantinoResponseDto);

        DirectorResponseDto actual = directorService.findById(validId);
        DirectorResponseDto expected = directorTarantinoResponseDto;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to find a director by invalid id")
    void findById_WithInvalidId_ThrowCustomException() {
        Mockito.when(directorRepository.findById(invalidId))
                .thenThrow(new EntityNotFoundException("Cannot find director by id " + invalidId));
        Assertions.assertThrows(
                EntityNotFoundException.class, () -> directorService.findById(invalidId));
    }

    @Test
    @DisplayName("Get all directors and return response dto page")
    void findAll_ReturnResponseDtoPage() {
        Mockito.when(directorRepository.findAll(pageable)).thenReturn(directorsPage);
        Mockito.when(directorMapper.toDto(directorTarantino))
                .thenReturn(directorTarantinoResponseDto);
        Mockito.when(directorMapper.toDto(directorNolan)).thenReturn(directorNolanResponseDto);

        Page<DirectorResponseDto> actual = directorService.findAll(pageable);
        Page<DirectorResponseDto> expected = directorResponseDtoPage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Search directors by name and return response dto page")
    void searchByName_WithValidName_ReturnDirectorsDtoPage() {
        Mockito.when(directorRepository.findByNameStartingWithIgnoreCase(searchParam, pageable))
                .thenReturn(directorsByNamePage);
        Mockito.when(directorMapper.toDto(directorTarantino))
                .thenReturn(directorTarantinoResponseDto);

        Page<DirectorResponseDto> actual = directorService.searchByName(searchParam, pageable);
        Page<DirectorResponseDto> expected = directorResponseDtoByNamePage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete director by id")
    void deleteById_WithValidId() {
        Mockito.doNothing().when(directorRepository).deleteById(validId);
        Assertions.assertDoesNotThrow(() -> directorService.deleteById(validId));
        Mockito.verify(directorRepository,
                Mockito.times(1)).deleteById(validId);
    }

    @Test
    @DisplayName("Update director and return updated response dto")
    void updateDirector_WithValidId_ReturnUpdatedResponseDto() {
        Mockito.when(directorRepository.findById(validId))
                .thenReturn(Optional.of(directorTarantino));
        Mockito.when(directorRepository.save(directorTarantino))
                .thenReturn(directorTarantino);
        Mockito.when(directorMapper.toDto(directorTarantino))
                .thenReturn(updatedDirectorTarantinoResponseDto);

        DirectorResponseDto actual = directorService.updateDirector(
                validId, directorTarantinoUpdateDto);
        DirectorResponseDto expected = updatedDirectorTarantinoResponseDto;

        Assertions.assertEquals(expected, actual);
    }
}
