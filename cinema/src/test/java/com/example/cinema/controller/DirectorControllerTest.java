package com.example.cinema.controller;

import com.example.cinema.CinemaApplication;
import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.extra.PageResponse;
import com.example.cinema.model.Director;
import com.example.cinema.model.User;
import com.example.cinema.repository.DirectorRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {
        "classpath:database/directors/insert-directors-into-directors-table.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/directors/delete-all-from-directors-table.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CinemaApplication.class)
class DirectorControllerTest {
    protected static MockMvc mockMvc;
    private static DirectorCreateDto createDtoOne;
    private static DirectorCreateDto createDtoTwo;
    private static DirectorCreateDto createDtoThree;
    private static DirectorResponseDto responseDtoOne;
    private static DirectorResponseDto responseDtoTwo;
    private static DirectorResponseDto responseDtoThree;
    private static DirectorResponseDto updDirectorResponseDto;
    private static DirectorUpdateDto directorUpdateDto;
    private static Page<DirectorResponseDto> directorResponseDtoPage;
    private static User user;
    private static Pageable pageable;
    private static Long counter = -2L;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DirectorRepository directorRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        createDtoOne = new DirectorCreateDto();
        createDtoTwo = new DirectorCreateDto();
        createDtoThree = new DirectorCreateDto();
        TestParamsInitUtil.initializeDirectorCreateDtos(
                createDtoOne,
                createDtoTwo,
                createDtoThree);

        responseDtoOne = new DirectorResponseDto();
        responseDtoTwo = new DirectorResponseDto();
        responseDtoThree = new DirectorResponseDto();
        TestParamsInitUtil.initializeDirectorResponseDtos(
                responseDtoOne,
                responseDtoTwo,
                responseDtoThree);

        user = new User();
        TestParamsInitUtil.initializeUserStuff(user);

        directorUpdateDto = new DirectorUpdateDto();
        updDirectorResponseDto = new DirectorResponseDto();
        TestParamsInitUtil.initializeDirectorUpdateDtos(directorUpdateDto, updDirectorResponseDto);

        List<DirectorResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(responseDtoOne);
        responseDtoList.add(responseDtoTwo);
        responseDtoList.add(responseDtoThree);

        pageable = PageRequest.of(0, 10);

        directorResponseDtoPage = new PageImpl<>(responseDtoList, pageable, responseDtoList.size());
    }

    @BeforeEach
    void setUp() {
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        counter += 3;
    }

//    @WithMockUser(username = "user")
//    @Test
//    @DisplayName("Return directorDto by id")
//    void findById_ValidId_ReturnDto() throws Exception {
//        MvcResult result = mockMvc.perform(get("/api/directors/" + counter)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        DirectorResponseDto expected = responseDtoOne;
//        DirectorResponseDto actual = objectMapper.readValue(
//                result.getResponse().getContentAsString(), DirectorResponseDto.class);
//
//        EqualsBuilder.reflectionEquals(expected, actual);
//    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Get a page of 3 directors mapped to dto from the DB ")
    void getAll_returnDirectorsDtoPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/directors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        PageResponse<DirectorResponseDto> pageResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });
        Page<DirectorResponseDto> actualPage = PageUtil.toPage(pageResponse, pageable);

        Assertions.assertEquals(directorResponseDtoPage.getTotalElements(), actualPage.getTotalElements());
        Assertions.assertEquals(directorResponseDtoPage.getTotalPages(), actualPage.getTotalPages());
        Assertions.assertEquals(directorResponseDtoPage.getNumber(), actualPage.getNumber());
    }

    @WithMockUser(username = "moderator")
    @Test
    @DisplayName("Add a new director")
    void save_ValidDirector_ReturnDirectorDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(createDtoOne);
        MvcResult result = mockMvc.perform(
                        post("/api/directors")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        DirectorResponseDto expected = responseDtoOne;
        DirectorResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), DirectorResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "moderator")
    @Test
    @DisplayName("Delete director by id")
    void delete_WithValidDirectorId_ResponseStatusOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        delete("/api/directors/" + ++counter)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Optional<Director> deletedDirector = directorRepository.findById(counter);
        Assertions.assertFalse(deletedDirector.isPresent());
    }

//    @WithMockUser(username = "moderator")
//    @Test
//    @DisplayName("Update director by id")
//    void update_WithValidDirector_ReturnUpdatedDirector() throws Exception {
//        String jsonRequest = objectMapper.writeValueAsString(directorUpdateDto);
//        MvcResult mvcResult = mockMvc.perform(
//                        patch("/api/directors/" + counter)
//                                .content(jsonRequest)
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andReturn();
//        DirectorResponseDto expected = updDirectorResponseDto;
//        DirectorResponseDto actual = objectMapper.readValue(
//                mvcResult.getResponse().getContentAsString(), DirectorResponseDto.class);
//        EqualsBuilder.reflectionEquals(expected, actual, "id");
//    }
}
