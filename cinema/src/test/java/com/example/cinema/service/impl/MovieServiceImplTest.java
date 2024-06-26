package com.example.cinema.service.impl;

import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.MovieMapper;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.movie.MovieRepository;
import com.example.cinema.repository.movie.MovieSpecificationBuilder;
import com.example.cinema.util.TestParamsInitUtil;
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
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    private static final String CANNOT_FIND_MOVIE_BY_ID_MSG = "Cannot find movie by id: ";

    private static Long validMovieId;
    private static Long invalidMovieId;
    private static Movie movieOne;
    private static Movie movieTwo;
    private static Movie movieThree;
    private static MovieResponseDto movieResponseDtoOne;
    private static MovieResponseDto movieResponseDtoTwo;
    private static MovieResponseDto movieResponseDtoThree;
    private static MovieCreateDto movieCreateDtoOne;
    private static MovieCreateDto movieCreateDtoTwo;
    private static MovieCreateDto movieCreateDtoThree;
    private static MovieUpdateDto movieUpdateDto;
    private static MovieResponseDto updatedMovieResponseDto;
    private static Pageable pageable;
    private static Page<Movie> moviePage;
    private static Page<MovieResponseDto> movieResponseDtoPage;

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper movieMapper;
    @Mock
    private Logger logger;
    @Mock
    private MovieSpecificationBuilder specificationBuilder;
    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeAll
    static void setUp() {
        validMovieId = 1L;
        invalidMovieId = 100L;

        movieOne = new Movie();
        movieTwo = new Movie();
        movieThree = new Movie();
        TestParamsInitUtil.initializeMovies(movieOne, movieTwo, movieThree);

        movieCreateDtoOne = new MovieCreateDto();
        movieCreateDtoTwo = new MovieCreateDto();
        movieCreateDtoThree = new MovieCreateDto();
        TestParamsInitUtil.initializeMovieCreateDtos(
                movieCreateDtoOne,
                movieCreateDtoTwo,
                movieCreateDtoThree
        );

        movieResponseDtoOne = new MovieResponseDto();
        movieResponseDtoTwo = new MovieResponseDto();
        movieResponseDtoThree = new MovieResponseDto();
        TestParamsInitUtil.initializeMovieResponseDtos(
                movieResponseDtoOne,
                movieResponseDtoTwo,
                movieResponseDtoThree);

        movieUpdateDto = new MovieUpdateDto();
        updatedMovieResponseDto = new MovieResponseDto();

        TestParamsInitUtil.initializeMovieUpdateDto(movieUpdateDto, updatedMovieResponseDto);
        List<Movie> movieList = List.of(movieOne, movieTwo, movieThree);
        List<MovieResponseDto> movieResponseDtoList = List.of(
                movieResponseDtoOne,
                movieResponseDtoTwo,
                movieResponseDtoThree);
        pageable = Pageable.unpaged();

        moviePage = new PageImpl<>(movieList, pageable, movieList.size());
        movieResponseDtoPage = new PageImpl<>(
                movieResponseDtoList, pageable, movieResponseDtoList.size());
    }

    @Test
    @DisplayName("Search movies by title and return response dto page")
    void searchByTitle_ReturnResponseDtoPage() {
        Mockito.when(movieRepository.findByTitleStartingWithIgnoreCase(pageable, "Inception"))
                .thenReturn(moviePage);
        Mockito.when(movieMapper.toDto(movieOne)).thenReturn(movieResponseDtoOne);
        Mockito.when(movieMapper.toDto(movieTwo)).thenReturn(movieResponseDtoTwo);
        Mockito.when(movieMapper.toDto(movieThree)).thenReturn(movieResponseDtoThree);

        Page<MovieResponseDto> actual = movieService.searchByTitle(pageable, "Inception");
        Page<MovieResponseDto> expected = movieResponseDtoPage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve all movies and return response dto page")
    void getAll_ReturnResponseDtoPage() {
        Mockito.when(movieRepository.findAll(pageable)).thenReturn(moviePage);
        Mockito.when(movieMapper.toDto(movieOne)).thenReturn(movieResponseDtoOne);
        Mockito.when(movieMapper.toDto(movieTwo)).thenReturn(movieResponseDtoTwo);
        Mockito.when(movieMapper.toDto(movieThree)).thenReturn(movieResponseDtoThree);

        Page<MovieResponseDto> actual = movieService.getAll(pageable);
        Page<MovieResponseDto> expected = movieResponseDtoPage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find movie by valid id and return response dto")
    void findById_WithValidId_ReturnResponseDto() {
        Mockito.when(movieRepository.findById(validMovieId)).thenReturn(Optional.of(movieOne));
        Mockito.when(movieMapper.toDto(movieOne)).thenReturn(movieResponseDtoOne);

        MovieResponseDto actual = movieService.findById(validMovieId);
        MovieResponseDto expected = movieResponseDtoOne;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to find a movie by invalid id")
    void findById_WithInvalidId_ThrowCustomException() {
        Mockito.when(movieRepository.findById(invalidMovieId))
                .thenThrow(
                        new EntityNotFoundException(CANNOT_FIND_MOVIE_BY_ID_MSG + invalidMovieId));

        Assertions.assertThrows(
                EntityNotFoundException.class, () -> movieService.findById(invalidMovieId));
    }

    @Test
    @DisplayName("Save new movie and return response dto")
    void saveMovie_WithValidDto_ReturnResponseDto() {
        Mockito.when(movieMapper.toModel(movieCreateDtoOne)).thenReturn(movieOne);
        Mockito.when(movieRepository.save(movieOne)).thenReturn(movieOne);
        Mockito.when(movieMapper.toDto(movieOne)).thenReturn(movieResponseDtoOne);

        MovieResponseDto actual = movieService.saveMovie(movieCreateDtoOne);
        MovieResponseDto expected = movieResponseDtoOne;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throw EntityNotFoundException when trying to update a movie by invalid id")
    void updateMovie_WithInvalidId_ThrowCustomException() {
        Mockito.when(movieRepository.findById(invalidMovieId))
                .thenThrow(
                        new EntityNotFoundException(CANNOT_FIND_MOVIE_BY_ID_MSG + invalidMovieId));

        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> movieService.updateMovie(invalidMovieId, movieUpdateDto));
    }

    @Test
    @DisplayName("Delete movie by id")
    void deleteById_WithValidId() {
        Mockito.doNothing().when(movieRepository).deleteById(validMovieId);
        Assertions.assertDoesNotThrow(() -> movieService.deleteById(validMovieId));
        Mockito.verify(movieRepository,
                Mockito.times(1)).deleteById(validMovieId);
    }
}
