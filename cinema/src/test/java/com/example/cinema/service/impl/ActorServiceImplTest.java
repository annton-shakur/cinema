package com.example.cinema.service.impl;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.ActorMapper;
import com.example.cinema.model.Actor;
import com.example.cinema.repository.ActorRepository;
import com.example.cinema.uitl.TestParamsInitUtil;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Logger;
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
class ActorServiceImplTest {

    private static Actor actorOne;
    private static Actor actorTwo;
    private static Actor actorThree;
    private static Actor actorOneWithoutId;
    private static ActorCreateDto actorOneCreateDto;
    private static ActorResponseDto actorOneResponseDto;
    private static ActorResponseDto actorTwoResponseDto;
    private static ActorUpdateDto actorOneUpdateDto;
    private static ActorResponseDto updatedActorOneResponseDto;
    private static ActorResponseDto actorThreeResponseDto;
    private static Long validId;
    private static Long invalidId;
    private static String searchParam;
    private static Page<ActorResponseDto> actorResponseDtoPage;
    private static Page<ActorResponseDto> actorResponseDtoByNamePage;
    private static Page<Actor> actorsPage;
    private static Page<Actor> actorsByNamePage;
    private static Pageable pageable;

    @Mock
    private ActorRepository actorRepository;
    @Mock
    private ActorMapper actorMapper;
    @Mock
    private Logger logger;
    @InjectMocks
    private ActorServiceImpl actorService;

    @BeforeAll
    static void beforeAll() {

        validId = 1L;
        invalidId = 100L;
        searchParam = "Leo";

        pageable = PageRequest.of(0, 10);

        actorOne = new Actor();
        actorTwo = new Actor();
        actorThree = new Actor();
        actorOneWithoutId = new Actor();
        TestParamsInitUtil.initializeActorModels(actorOne, actorTwo,
                actorThree, actorOneWithoutId);

        actorOneCreateDto = new ActorCreateDto();
        actorOneResponseDto = new ActorResponseDto();
        actorTwoResponseDto = new ActorResponseDto();
        actorThreeResponseDto = new ActorResponseDto();
        actorOneUpdateDto = new ActorUpdateDto();
        updatedActorOneResponseDto = new ActorResponseDto();
        TestParamsInitUtil.initializeActorDtos(actorOneCreateDto, actorOneResponseDto,
                actorTwoResponseDto, actorThreeResponseDto,
                actorOneUpdateDto, updatedActorOneResponseDto
        );

        List<ActorResponseDto> actorResponseDtoList = List.of(
                actorOneResponseDto, actorTwoResponseDto, actorThreeResponseDto);
        final List<ActorResponseDto> actorResponseDtoByNameList = List.of(
                actorOneResponseDto, actorThreeResponseDto);

        List<Actor> actorsList = List.of(actorOne, actorTwo, actorThree);
        List<Actor> actorsByNameList = List.of(actorOne, actorThree);
        actorsPage = new PageImpl<>(actorsList, pageable, actorsList.size());
        actorsByNamePage = new PageImpl<>(actorsByNameList, pageable, actorsByNameList.size());

        actorResponseDtoPage = new PageImpl<>(
                actorResponseDtoList, pageable, actorResponseDtoList.size());
        actorResponseDtoByNamePage = new PageImpl<>(
                actorResponseDtoByNameList, pageable, actorResponseDtoByNameList.size());
    }

    @Test
    @DisplayName("Save new actor and return response dto")
    void saveActor_WithValidDto_ReturnResponseDto() {
        Mockito.when(actorMapper.toModel(actorOneCreateDto))
                .thenReturn(actorOneWithoutId);
        Mockito.when(actorRepository.save(actorOneWithoutId))
                .thenReturn(actorOneWithoutId);
        Mockito.when(actorMapper.toDto(actorOneWithoutId))
                .thenReturn(actorOneResponseDto);

        ActorResponseDto actual = actorService.saveActor(actorOneCreateDto);
        ActorResponseDto expected = actorOneResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find actor by valid id and return its dto")
    void findById_WithValidId_ReturnResponseDto() {
        Mockito.when(actorRepository.findById(validId))
                .thenReturn(Optional.of(actorOne));
        Mockito.when(actorMapper.toDto(actorOne))
                .thenReturn(actorOneResponseDto);

        ActorResponseDto actual = actorService.findById(validId);
        ActorResponseDto expected = actorOneResponseDto;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to find an actor by invalid id")
    void findById_WithInvalidId_ThrowCustomException() {
        Mockito.when(actorRepository.findById(invalidId))
                .thenThrow(new EntityNotFoundException("Cannot find actor by id " + invalidId));
        Assertions.assertThrows(
                EntityNotFoundException.class, () -> actorService.findById(invalidId));
    }

    @Test
    @DisplayName("Get all actors and return response dto page")
    void getAllActors_ReturnResponseDtoPage() {
        Mockito.when(actorRepository.findAll(pageable)).thenReturn(actorsPage);
        Mockito.when(actorMapper.toDto(actorOne)).thenReturn(actorOneResponseDto);
        Mockito.when(actorMapper.toDto(actorTwo)).thenReturn(actorTwoResponseDto);
        Mockito.when(actorMapper.toDto(actorThree)).thenReturn(actorThreeResponseDto);

        Page<ActorResponseDto> actualPage = actorService.getAll(pageable);
        Page<ActorResponseDto> expectedPage = actorResponseDtoPage;

        Assertions.assertEquals(expectedPage.getTotalElements(), actualPage.getTotalElements());
        Assertions.assertEquals(expectedPage.getContent(), actualPage.getContent());
    }

    @Test
    @DisplayName("Search actors by name starting with 'Leo' and return response dto page")
    void searchByName_WithValidName_ReturnActorsDtoPage() {
        Mockito.when(actorRepository.findByNameStartingWithIgnoreCase(searchParam, pageable))
                .thenReturn(actorsByNamePage);
        Mockito.when(actorMapper.toDto(actorOne)).thenReturn(actorOneResponseDto);
        Mockito.when(actorMapper.toDto(actorThree)).thenReturn(actorThreeResponseDto);

        Page<ActorResponseDto> actual = actorService.searchByName(searchParam, pageable);
        Page<ActorResponseDto> expected = actorResponseDtoByNamePage;

        Assertions.assertEquals(expected.getTotalElements(), actual.getTotalElements());
        Assertions.assertEquals(expected.getContent(), actual.getContent());
    }

    @Test
    @DisplayName("Update actor with valid id and return updated response dto")
    void updateById_WithValidId_ReturnUpdatedResponseDto() {
        Mockito.when(actorRepository.findById(validId)).thenReturn(Optional.of(actorOne));
        Mockito.when(actorRepository.save(actorOne)).thenReturn(actorOne);
        Mockito.when(actorMapper.toDto(actorOne)).thenReturn(updatedActorOneResponseDto);

        ActorResponseDto actual = actorService.updateById(validId, actorOneUpdateDto);
        ActorResponseDto expected = updatedActorOneResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete actor by valid id")
    void deleteById_WithValidId_PerformDeletion() {
        Mockito.doNothing().when(actorRepository).deleteById(validId);
        actorService.deleteById(validId);
        Mockito.verify(actorRepository, Mockito.times(1)).deleteById(validId);
    }
}
