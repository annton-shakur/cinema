package com.example.cinema.service.impl;

import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.MovieMapper;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.movie.MovieRepository;
import com.example.cinema.repository.movie.MovieSpecificationBuilder;
import com.example.cinema.uitl.TestParamsInitUtil;
import java.time.LocalDate;
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
    private static Movie movie;
    private static MovieResponseDto movieResponseDto;
    private static MovieCreateDto movieCreateDto;
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

        movie = new Movie();
        movieCreateDto = new MovieCreateDto();
        movieUpdateDto = new MovieUpdateDto();
        movieResponseDto = new MovieResponseDto();
        updatedMovieResponseDto = new MovieResponseDto();
        TestParamsInitUtil.initializeAllMovieFields(movie, movieCreateDto,
                movieUpdateDto, movieResponseDto, updatedMovieResponseDto);
        List<Movie> movieList = List.of(movie);
        List<MovieResponseDto> movieResponseDtoList = List.of(movieResponseDto);
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
        Mockito.when(movieMapper.toDto(movie)).thenReturn(movieResponseDto);

        Page<MovieResponseDto> actual = movieService.searchByTitle(pageable, "Inception");
        Page<MovieResponseDto> expected = movieResponseDtoPage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Retrieve all movies and return response dto page")
    void getAll_ReturnResponseDtoPage() {
        Mockito.when(movieRepository.findAll(pageable)).thenReturn(moviePage);
        Mockito.when(movieMapper.toDto(movie)).thenReturn(movieResponseDto);

        Page<MovieResponseDto> actual = movieService.getAll(pageable);
        Page<MovieResponseDto> expected = movieResponseDtoPage;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find movie by valid id and return response dto")
    void findById_WithValidId_ReturnResponseDto() {
        Mockito.when(movieRepository.findById(validMovieId)).thenReturn(Optional.of(movie));
        Mockito.when(movieMapper.toDto(movie)).thenReturn(movieResponseDto);

        MovieResponseDto actual = movieService.findById(validMovieId);
        MovieResponseDto expected = movieResponseDto;

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
        Mockito.when(movieMapper.toModel(movieCreateDto)).thenReturn(movie);
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        Mockito.when(movieMapper.toDto(movie)).thenReturn(movieResponseDto);

        MovieResponseDto actual = movieService.saveMovie(movieCreateDto);
        MovieResponseDto expected = movieResponseDto;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update movie by valid id and return updated response dto")
    void updateMovie_WithValidId_ReturnUpdatedResponseDto() {
        Mockito.when(movieRepository.findById(validMovieId)).thenReturn(Optional.of(movie));
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        Mockito.when(movieMapper.toDto(movie)).thenReturn(updatedMovieResponseDto);

        final MovieResponseDto actual = movieService.updateMovie(validMovieId, movieUpdateDto);
        MovieResponseDto expected = new MovieResponseDto();
        expected.setId(validMovieId);
        expected.setTitle("Inception: Director's Cut");
        expected.setDuration(152);
        expected.setDescription("""
                A thief who steals corporate secrets through the use of dream-sharing
                technology is given the inverse task of planting an idea into the mind
                of a C.E.O. (Director's Cut)
                """);
        expected.setTrailerUrl("http://example.com/inception_directors_cut_trailer");
        expected.setReleaseDate(LocalDate.of(2021, 7, 16));
        expected.setDirectorId(1L);
        expected.setActorIds(List.of(1L, 2L));
        expected.setCategoryIds(List.of(1L, 2L));
        expected.setCommentIds(List.of());

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
