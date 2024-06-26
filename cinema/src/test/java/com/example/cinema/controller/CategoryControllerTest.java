package com.example.cinema.controller;

import com.example.cinema.CinemaApplication;
import com.example.cinema.dto.category.CategoryCreateDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.category.CategoryUpdateDto;
import com.example.cinema.extra.PageResponse;
import com.example.cinema.model.Category;
import com.example.cinema.model.User;
import com.example.cinema.repository.CategoryRepository;
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
        "classpath:database/categories/insert-categories-into-categories-table.sql"
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {
        "classpath:database/categories/delete-all-from-categories-table.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CinemaApplication.class)
class CategoryControllerTest {

    protected static MockMvc mockMvc;
    private static CategoryCreateDto createDtoOne;
    private static CategoryCreateDto createDtoTwo;
    private static CategoryCreateDto createDtoThree;
    private static CategoryResponseDto responseDtoOne;
    private static CategoryResponseDto responseDtoTwo;
    private static CategoryResponseDto responseDtoThree;
    private static CategoryResponseDto updCategoryResponseDto;
    private static CategoryUpdateDto categoryUpdateDto;
    private static Page<CategoryResponseDto> categoryResponseDtoPage;
    private static User user;
    private static Pageable pageable;
    private static Long counter = -2L;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();

        createDtoOne = new CategoryCreateDto();
        createDtoTwo = new CategoryCreateDto();
        createDtoThree = new CategoryCreateDto();
        TestParamsInitUtil.initializeCategoryCreateDtos(
                createDtoOne,
                createDtoTwo,
                createDtoThree);

        responseDtoOne = new CategoryResponseDto();
        responseDtoTwo = new CategoryResponseDto();
        responseDtoThree = new CategoryResponseDto();
        TestParamsInitUtil.initializeCategoryResponseDtos(
                responseDtoOne,
                responseDtoTwo,
                responseDtoThree);

        user = new User();
        TestParamsInitUtil.initializeUserStuff(user);

        categoryUpdateDto = new CategoryUpdateDto();
        updCategoryResponseDto = new CategoryResponseDto();
        TestParamsInitUtil.initializeCategoryUpdateDto(categoryUpdateDto, updCategoryResponseDto);

        List<CategoryResponseDto> responseDtoList = new ArrayList<>();
        responseDtoList.add(responseDtoOne);
        responseDtoList.add(responseDtoTwo);
        responseDtoList.add(responseDtoThree);

        pageable = PageRequest.of(0, 10);

        categoryResponseDtoPage = new PageImpl<>(responseDtoList, pageable, responseDtoList.size());
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
    @DisplayName("Return CategoryResponseDto by id")
    void findById_ValidId_ReturnDto() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/categories/" + counter)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryResponseDto expected = responseDtoOne;
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Get a page of 3 categories mapped to dto from the DB")
    void getAll_returnCategoriesDtoPage() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        PageResponse<CategoryResponseDto> pageResponse = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });
        Page<CategoryResponseDto> actualPage = PageUtil.toPage(pageResponse, pageable);

        Assertions.assertEquals(categoryResponseDtoPage.getTotalElements(), actualPage.getTotalElements());
        Assertions.assertEquals(categoryResponseDtoPage.getTotalPages(), actualPage.getTotalPages());
        Assertions.assertEquals(categoryResponseDtoPage.getNumber(), actualPage.getNumber());
    }

    @WithMockUser(username = "moderator")
    @Test
    @DisplayName("Add a new category")
    void save_ValidCategory_ReturnCategoryDto() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(createDtoOne);
        MvcResult result = mockMvc.perform(
                        post("/api/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        CategoryResponseDto expected = responseDtoOne;
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "moderator")
    @Test
    @DisplayName("Delete category by id")
    void delete_WithValidCategoryId_ResponseStatusOk() throws Exception {
        MvcResult result = mockMvc.perform(
                        delete("/api/categories/" + ++counter)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        Optional<Category> deletedCategory = categoryRepository.findById(counter);
        Assertions.assertFalse(deletedCategory.isPresent());
    }

    @WithMockUser(username = "moderator")
    @Test
    @DisplayName("Update category by id")
    void update_WithValidCategory_ReturnUpdatedCategory() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(categoryUpdateDto);
        MvcResult mvcResult = mockMvc.perform(
                        patch("/api/categories/" + ++counter)
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        CategoryResponseDto expected = updCategoryResponseDto;
        CategoryResponseDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CategoryResponseDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("Search categories by name")
    void searchByName_ValidName_ReturnCategoryList() throws Exception {
        String searchName = "Action";
        MvcResult result = mockMvc.perform(get("/api/categories/search")
                        .param("name", searchName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryResponseDto> expected = List.of(responseDtoOne);
        List<CategoryResponseDto> actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {
                });

        Assertions.assertEquals(expected.size(), actual.size());
        EqualsBuilder.reflectionEquals(expected.getFirst(), actual.getFirst(), "id");
    }
}
