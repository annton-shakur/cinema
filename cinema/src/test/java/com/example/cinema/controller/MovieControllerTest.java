package com.example.cinema.controller;

import com.example.cinema.CinemaApplication;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.extra.PageResponse;
import com.example.cinema.model.Movie;
import com.example.cinema.model.User;
import com.example.cinema.repository.movie.MovieRepository;
import com.example.cinema.util.PageUtil;
import com.example.cinema.util.TestParamsInitUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {
        "classpath:database/directors/insert-directors-into-directors-table.sql",
        "classpath:database/movies/insert-movies-into-movies-table.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/movies/delete-all-from-movies.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CinemaApplication.class)
class MovieControllerTest {
    protected static MockMvc mockMvc;
    private static MovieCreateDto createDtoOne;
    private static MovieCreateDto createDtoTwo;
    private static MovieCreateDto createDtoThree;
    private static MovieResponseDto responseDtoOne;
    private static MovieResponseDto responseDtoTwo;
    private static MovieResponseDto responseDtoThree;
    private static MovieResponseDto updMovieResponseDto;
    private static MovieUpdateDto movieUpdateDto;
    private static User user;
    private static Pageable pageable;
    private static Page<MovieResponseDto> movieResponseDtoPage;
    private static int counter = -2;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieRepository movieRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        createDtoOne = new MovieCreateDto();
        createDtoTwo = new MovieCreateDto();
        createDtoThree = new MovieCreateDto();
        TestParamsInitUtil.initializeMovieCreateDtos(createDtoOne, createDtoTwo, createDtoThree);

        responseDtoOne = new MovieResponseDto();
        responseDtoTwo = new MovieResponseDto();
        responseDtoThree = new MovieResponseDto();
        TestParamsInitUtil.initializeMovieResponseDtos(responseDtoOne, responseDtoTwo, responseDtoThree);

        user = new User();
        TestParamsInitUtil.initializeUserStuff(user);

        movieUpdateDto = new MovieUpdateDto();
        updMovieResponseDto = new MovieResponseDto();
        TestParamsInitUtil.initializeMovieUpdateDto(movieUpdateDto, updMovieResponseDto);

        List<MovieResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(responseDtoOne);
        responseDtoList.add(responseDtoTwo);
        responseDtoList.add(responseDtoThree);

        pageable = PageRequest.of(0, 10);

        movieResponseDtoPage = new PageImpl<>(responseDtoList, pageable, responseDtoList.size());
    }

    @BeforeEach
    void setUp() {
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        counter += 3;
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Return moviesDto by id")
    void findById_ValidId_ReturnDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/movies/" + counter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        MovieResponseDto expected = responseDtoOne;
        MovieResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), MovieResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Get a page of movies mapped to dto from the DB ")
    void getAll_returnMoviesDtoPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        PageResponse<MovieResponseDto> pageResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });
        Page<MovieResponseDto> actualPage = PageUtil.toPage(pageResponse, pageable);

        Assertions.assertEquals(movieResponseDtoPage.getTotalElements(), actualPage.getTotalElements());
        Assertions.assertEquals(movieResponseDtoPage.getTotalPages(), actualPage.getTotalPages());
        Assertions.assertEquals(movieResponseDtoPage.getNumber(), actualPage.getNumber());
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Add a new movie")
    void save_ValidMovie_ReturnMovieDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(createDtoOne);
        MvcResult result = mockMvc.perform(
                        post("/api/movies")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        MovieResponseDto expected = responseDtoOne;
        MovieResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), MovieResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Delete movie by id")
    void delete_WithValidMovieId_ResponseStatusOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        delete("/api/movies/" + counter)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Optional<Movie> deletedMovie = movieRepository.findById(1L);
        Assertions.assertFalse(deletedMovie.isPresent());
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Update movie by id")
    void update_WithValidMovie_ReturnUpdatedMovie() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(movieUpdateDto);
        MvcResult mvcResult = mockMvc.perform(
                        patch("/api/movies/" + (counter + 1))
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        MovieResponseDto expected = updMovieResponseDto;
        MovieResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), MovieResponseDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
