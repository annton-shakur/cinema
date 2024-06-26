package com.example.cinema.controller;

import com.example.cinema.CinemaApplication;
import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.extra.PageResponse;
import com.example.cinema.model.Actor;
import com.example.cinema.model.User;
import com.example.cinema.repository.ActorRepository;
import com.example.cinema.util.PageUtil;
import com.example.cinema.util.TestParamsInitUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {
        "classpath:database/actors/insert-actors-into-actors-table.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/actors/delete-all-from-actors-table.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CinemaApplication.class)
class ActorControllerTest {
    protected static MockMvc mockMvc;
    private static ActorCreateDto createDtoOne;
    private static ActorCreateDto createDtoTwo;
    private static ActorCreateDto createDtoThree;
    private static ActorCreateDto createDtoFour;
    private static ActorCreateDto createDtoFive;
    private static ActorResponseDto responseDtoOne;
    private static ActorResponseDto responseDtoTwo;
    private static ActorResponseDto responseDtoThree;
    private static ActorResponseDto responseDtoFour;
    private static ActorResponseDto responseDtoFive;
    private static ActorResponseDto updActorResponseDto;
    private static ActorUpdateDto actorUpdateDto;
    private static Page<ActorResponseDto> actorResponseDtoPage;
    private static User user;
    private static Pageable pageable;
    private static int counter = -4;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ActorRepository actorRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        createDtoOne = new ActorCreateDto();
        createDtoTwo = new ActorCreateDto();
        createDtoThree = new ActorCreateDto();
        createDtoFour = new ActorCreateDto();
        createDtoFive = new ActorCreateDto();
        TestParamsInitUtil.initializeActorCreateDtos(
                createDtoOne,
                createDtoTwo,
                createDtoThree,
                createDtoFour,
                createDtoFive);
        responseDtoOne = new ActorResponseDto();
        responseDtoTwo = new ActorResponseDto();
        responseDtoThree = new ActorResponseDto();
        responseDtoFour = new ActorResponseDto();
        responseDtoFive = new ActorResponseDto();
        TestParamsInitUtil.initializeActorResponseDtos(
                responseDtoOne,
                responseDtoTwo,
                responseDtoThree,
                responseDtoFour,
                responseDtoFive);
        user = new User();
        TestParamsInitUtil.initializeUserStuff(user);
        actorUpdateDto = new ActorUpdateDto();
        updActorResponseDto = new ActorResponseDto();
        TestParamsInitUtil.initializeActorUpdateDto(actorUpdateDto, updActorResponseDto);
        List<ActorResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(responseDtoOne);
        responseDtoList.add(responseDtoTwo);
        responseDtoList.add(responseDtoThree);
        responseDtoList.add(responseDtoFour);
        responseDtoList.add(responseDtoFive);

        pageable = PageRequest.of(0, 10);

        actorResponseDtoPage = new PageImpl<>(responseDtoList, pageable, responseDtoList.size());
    }

    @BeforeEach
    void setUp() {
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(user,
                user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        counter += 5;
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Return actorsDto by id")
    void findById_ValidId_ReturnDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/actors/" + counter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ActorResponseDto expected = responseDtoOne;
        ActorResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ActorResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Get a page of 5 actors mapped to dto from the DB ")
    void getAll_returnActorsDtoPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/actors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        PageResponse<ActorResponseDto> pageResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });
        Page<ActorResponseDto> actualPage = PageUtil.toPage(pageResponse, pageable);

        Assertions.assertEquals(actorResponseDtoPage.getTotalElements(), actualPage.getTotalElements());
        Assertions.assertEquals(actorResponseDtoPage.getTotalPages(), actualPage.getTotalPages());
        Assertions.assertEquals(actorResponseDtoPage.getNumber(), actualPage.getNumber());
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Add a new actor")
    void save_ValidActor_ReturnActorDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(createDtoOne);
        MvcResult result = mockMvc.perform(
                        post("/api/actors")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        ActorResponseDto expected = responseDtoOne;
        ActorResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ActorResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Delete actor by id")
    void delete_WithValidBookId_ResponseStatusOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        delete("/api/actors/" + counter)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Optional<Actor> deletedActor = actorRepository.findById(1L);
        Assertions.assertFalse(deletedActor.isPresent());
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Update actor by id")
    void update_WithValidBook_ReturnUpdateBook() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(updActorResponseDto);
        MvcResult mvcResult = mockMvc.perform(
                        patch("/api/actors/" + (counter + 1))
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        ActorResponseDto expected = updActorResponseDto;
        ActorResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), ActorResponseDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
