package com.example.cinema.util;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.dto.category.CategoryCreateDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.category.CategoryUpdateDto;
import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.model.Actor;
import com.example.cinema.model.Category;
import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Role;
import com.example.cinema.model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TestParamsInitUtil {

    public TestParamsInitUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static void initializeActorModels(
            Actor actorOne,
            Actor actorTwo,
            Actor actorThree,
            Actor actorFour,
            Actor actorFive) {
        actorOne.setName("Leonardo DiCaprio");
        actorOne.setAge(48);
        actorOne.setDescription("American actor and film producer");
        actorOne.setImageUrl("https://someurl.com");
        actorOne.setDeleted(false);

        actorTwo.setName("Brad Pitt");
        actorTwo.setAge(59);
        actorTwo.setDescription("American actor and film producer");
        actorTwo.setImageUrl("https://someurl.com");
        actorTwo.setDeleted(false);

        actorThree.setName("Anne Hathaway");
        actorThree.setAge(40);
        actorThree.setDescription("American actress");
        actorThree.setImageUrl("https://someurl.com");
        actorThree.setDeleted(false);

        actorFour.setName("Robert De Niro");
        actorFour.setAge(79);
        actorFour.setDescription("American actor, director, producer, and screenwriter");
        actorFour.setImageUrl("https://someurl.com");
        actorFour.setDeleted(false);

        actorFive.setName("Ana de Armas");
        actorFive.setAge(34);
        actorFive.setDescription("Cuban and Spanish actress");
        actorFive.setImageUrl("https://someurl.com");
        actorFive.setDeleted(false);
    }

    public static void initializeActorCreateDtos(
            ActorCreateDto dtoOne,
            ActorCreateDto dtoTwo,
            ActorCreateDto dtoThree,
            ActorCreateDto dtoFour,
            ActorCreateDto dtoFive) {

        dtoOne.setName("Leonardo DiCaprio");
        dtoOne.setAge(48);
        dtoOne.setDescription("American actor and film producer");
        dtoOne.setImageUrl("https://someurl.com");

        dtoTwo.setName("Brad Pitt");
        dtoTwo.setAge(59);
        dtoTwo.setDescription("American actor and film producer");
        dtoTwo.setImageUrl("https://someurl.com");

        dtoThree.setName("Anne Hathaway");
        dtoThree.setAge(40);
        dtoThree.setDescription("American actress");
        dtoThree.setImageUrl("https://someurl.com");

        dtoFour.setName("Robert De Niro");
        dtoFour.setAge(79);
        dtoFour.setDescription("American actor, director, producer, and screenwriter");
        dtoFour.setImageUrl("https://someurl.com");

        dtoFive.setName("Ana de Armas");
        dtoFive.setAge(34);
        dtoFive.setDescription("Cuban and Spanish actress");
        dtoFive.setImageUrl("https://someurl.com");
    }

    public static void initializeActorResponseDtos(
            ActorResponseDto dtoOne,
            ActorResponseDto dtoTwo,
            ActorResponseDto dtoThree,
            ActorResponseDto dtoFour,
            ActorResponseDto dtoFive) {

        dtoOne.setId(1L);
        dtoOne.setName("Leonardo DiCaprio");
        dtoOne.setAge(48);
        dtoOne.setDescription("American actor and film producer");
        dtoOne.setImageUrl("https://someurl.com");

        dtoTwo.setId(2L);
        dtoTwo.setName("Brad Pitt");
        dtoTwo.setAge(59);
        dtoTwo.setDescription("American actor and film producer");
        dtoTwo.setImageUrl("https://someurl.com");

        dtoThree.setId(3L);
        dtoThree.setName("Anne Hathaway");
        dtoThree.setAge(40);
        dtoThree.setDescription("American actress");
        dtoThree.setImageUrl("https://someurl.com");

        dtoFour.setId(4L);
        dtoFour.setName("Robert De Niro");
        dtoFour.setAge(79);
        dtoFour.setDescription("American actor, director, producer, and screenwriter");
        dtoFour.setImageUrl("https://someurl.com");

        dtoFive.setId(5L);
        dtoFive.setName("Ana de Armas");
        dtoFive.setAge(34);
        dtoFive.setDescription("Cuban and Spanish actress");
        dtoFive.setImageUrl("https://someurl.com");
    }

    public static void initializeUserStuff(User user) {
        Role userRole = new Role(1L, Role.RoleName.USER);
        Role adminRole = new Role(2L, Role.RoleName.MODERATOR);
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword("somedifficultpassword");
        user.setImageUrl("https://image.com");
        user.setFirstName("Anton");
        user.setLastName("Dudar");
        user.setRoles(new HashSet<>(Set.of(userRole, adminRole)));
    }

    public static void initializeActorUpdateDto(ActorUpdateDto actorUpdateDto,
                                                ActorResponseDto actorResponseDto) {
        actorUpdateDto.setAge(100);
        actorUpdateDto.setName("Updated actor name");
        actorUpdateDto.setDescription("Updated description");
        actorUpdateDto.setImageUrl("https://updated.com");

        actorResponseDto.setId(1L);
        actorResponseDto.setAge(100);
        actorResponseDto.setName("Updated actor name");
        actorResponseDto.setDescription("Updated description");
        actorResponseDto.setImageUrl("https://updated.com");
    }

    public static void initializeDirectorCreateDtos(
            DirectorCreateDto dtoOne,
            DirectorCreateDto dtoTwo,
            DirectorCreateDto dtoThree) {

        dtoOne.setName("Quentin Tarantino");
        dtoOne.setDescription("American director, screenwriter, and producer");
        dtoOne.setImageUrl("https://someurl.com");

        dtoTwo.setName("Steven Spielberg");
        dtoTwo.setDescription("American film director, producer, and screenwriter");
        dtoTwo.setImageUrl("https://someurl.com");

        dtoThree.setName("Christopher Nolan");
        dtoThree.setDescription("British-American film director, screenwriter, and producer");
        dtoThree.setImageUrl("https://someurl.com");
    }

    public static void initializeDirectorResponseDtos(
            DirectorResponseDto dtoOne,
            DirectorResponseDto dtoTwo,
            DirectorResponseDto dtoThree) {

        dtoOne.setId(1L);
        dtoOne.setName("Quentin Tarantino");
        dtoOne.setDescription("American director, screenwriter, and producer");
        dtoOne.setImageUrl("https://someurl.com");

        dtoTwo.setId(2L);
        dtoTwo.setName("Steven Spielberg");
        dtoTwo.setDescription("American film director, producer, and screenwriter");
        dtoTwo.setImageUrl("https://someurl.com");

        dtoThree.setId(3L);
        dtoThree.setName("Christopher Nolan");
        dtoThree.setDescription("British-American film director, screenwriter, and producer");
        dtoThree.setImageUrl("https://someurl.com");
    }

    public static void initializeDirectors(
            Director directorOne,
            Director directorTwo,
            Director directorThree) {

        directorOne.setId(1L);
        directorOne.setName("Quentin Tarantino");
        directorOne.setDescription("American director, screenwriter, and producer");
        directorOne.setImageUrl("https://someurl.com");

        directorTwo.setId(2L);
        directorTwo.setName("Steven Spielberg");
        directorTwo.setDescription("American film director, producer, and screenwriter");
        directorTwo.setImageUrl("https://someurl.com");

        directorThree.setId(3L);
        directorThree.setName("Christopher Nolan");
        directorThree.setDescription("British-American film director, screenwriter, and producer");
        directorThree.setImageUrl("https://someurl.com");
    }


    public static void initializeDirectorUpdateDtos(
            DirectorUpdateDto updateDto,
            DirectorResponseDto responseDto) {
        updateDto.setName("Updated Director name");
        updateDto.setDescription("Updated description");
        updateDto.setImageUrl("https://updated.com");

        responseDto.setId(1L);
        responseDto.setName("Updated Director name");
        responseDto.setDescription("Updated description");
        responseDto.setImageUrl("https://updated.com");
    }

    public static void initializeCategories(
            Category categoryOne,
            Category categoryTwo,
            Category categoryThree) {

        categoryOne.setId(1L);
        categoryOne.setName("Action");

        categoryTwo.setId(2L);
        categoryTwo.setName("Comedy");

        categoryThree.setId(3L);
        categoryThree.setName("Drama");
    }

    public static void initializeCategoryCreateDtos(
            CategoryCreateDto dtoOne,
            CategoryCreateDto dtoTwo,
            CategoryCreateDto dtoThree) {

        dtoOne.setName("Action");
        dtoTwo.setName("Comedy");
        dtoThree.setName("Drama");
    }

    public static void initializeCategoryResponseDtos(
            CategoryResponseDto dtoOne,
            CategoryResponseDto dtoTwo,
            CategoryResponseDto dtoThree) {

        dtoOne.setId(1L);
        dtoOne.setName("Action");

        dtoTwo.setId(2L);
        dtoTwo.setName("Comedy");

        dtoThree.setId(3L);
        dtoThree.setName("Drama");
    }

    public static void initializeCategoryUpdateDto(
            CategoryUpdateDto updateDto,
            CategoryResponseDto responseDto) {
        updateDto.setName("Updated Category Name");
        responseDto.setId(1L);

        responseDto.setName("Updated Category Name");
    }

    public static void initializeMovies(
            Movie movieOne,
            Movie movieTwo,
            Movie movieThree) {

        Director directorOne = new Director();
        Director directorTwo = new Director();
        Director directorThree = new Director();
        initializeDirectors(directorOne, directorTwo, directorThree);

        // Actors and categories initialization removed

        movieOne.setTitle("Inception");
        movieOne.setDuration(148);
        movieOne.setDescription("A mind-bending thriller");
        movieOne.setTrailerUrl("https://trailerurl.com");
        movieOne.setReleaseDate(LocalDate.of(2010, 7, 16));
        movieOne.setDirector(directorThree);
        movieOne.setImageUrl("https://imageurl.com");
        movieOne.setActors(new HashSet<>()); // Empty collection
        movieOne.setCategories(new HashSet<>()); // Empty collection
        movieOne.setAverageRating(0.0);

        movieTwo.setTitle("Jurassic Park");
        movieTwo.setDuration(127);
        movieTwo.setDescription("Dinosaurs come to life");
        movieTwo.setTrailerUrl("https://trailerurl.com");
        movieTwo.setReleaseDate(LocalDate.of(1993, 6, 11));
        movieTwo.setDirector(directorTwo);
        movieTwo.setImageUrl("https://imageurl.com");
        movieTwo.setActors(new HashSet<>()); // Empty collection
        movieTwo.setCategories(new HashSet<>()); // Empty collection
        movieTwo.setAverageRating(0.0);

        movieThree.setTitle("Pulp Fiction");
        movieThree.setDuration(154);
        movieThree.setDescription("Interconnected stories");
        movieThree.setTrailerUrl("https://trailerurl.com");
        movieThree.setReleaseDate(LocalDate.of(1994, 10, 14));
        movieThree.setDirector(directorOne);
        movieThree.setImageUrl("https://imageurl.com");
        movieThree.setActors(new HashSet<>()); // Empty collection
        movieThree.setCategories(new HashSet<>()); // Empty collection
        movieThree.setAverageRating(0.0);
        movieThree.setDeleted(false);
    }

    public static void initializeMovieCreateDtos(
            MovieCreateDto movieOne,
            MovieCreateDto movieTwo,
            MovieCreateDto movieThree) {

        movieOne.setTitle("Inception");
        movieOne.setDuration(148);
        movieOne.setDescription("A mind-bending thriller");
        movieOne.setTrailerUrl("https://trailerurl.com");
        movieOne.setReleaseDate(LocalDate.of(2010, 7, 16));
        movieOne.setDirectorId(3L);
        movieOne.setImageUrl("https://imageurl.com");
        movieOne.setActorIds(new ArrayList<>()); // Empty collection
        movieOne.setCategoryIds(new ArrayList<>()); // Empty collection

        movieTwo.setTitle("Jurassic Park");
        movieTwo.setDuration(127);
        movieTwo.setDescription("Dinosaurs come to life");
        movieTwo.setTrailerUrl("https://trailerurl.com");
        movieTwo.setReleaseDate(LocalDate.of(1993, 6, 11));
        movieTwo.setDirectorId(2L);
        movieTwo.setImageUrl("https://imageurl.com");
        movieTwo.setActorIds(new ArrayList<>()); // Empty collection
        movieTwo.setCategoryIds(new ArrayList<>()); // Empty collection

        movieThree.setTitle("Pulp Fiction");
        movieThree.setDuration(154);
        movieThree.setDescription("Interconnected stories");
        movieThree.setTrailerUrl("https://trailerurl.com");
        movieThree.setReleaseDate(LocalDate.of(1994, 10, 14));
        movieThree.setDirectorId(1L);
        movieThree.setImageUrl("https://imageurl.com");
        movieThree.setActorIds(new ArrayList<>()); // Empty collection
        movieThree.setCategoryIds(new ArrayList<>()); // Empty collection
    }

    public static void initializeMovieResponseDtos(
            MovieResponseDto movieOne,
            MovieResponseDto movieTwo,
            MovieResponseDto movieThree) {

        movieOne.setId(1L);
        movieOne.setTitle("Inception");
        movieOne.setDuration(148);
        movieOne.setDescription("A mind-bending thriller");
        movieOne.setTrailerUrl("https://trailerurl.com");
        movieOne.setReleaseDate(LocalDate.of(2010, 7, 16));
        DirectorResponseDto directorOne = new DirectorResponseDto();
        directorOne.setId(3L);
        directorOne.setName("Christopher Nolan");
        movieOne.setDirector(directorOne);
        movieOne.setAverageRating(0.0);
        movieOne.setImageUrl("https://imageurl.com");
        movieOne.setActors(new ArrayList<>()); // Empty collection
        movieOne.setCategories(new ArrayList<>()); // Empty collection
        movieOne.setComments(new ArrayList<>()); // Assuming comments should be empty as well

        movieTwo.setId(2L);
        movieTwo.setTitle("Jurassic Park");
        movieTwo.setDuration(127);
        movieTwo.setDescription("Dinosaurs come to life");
        movieTwo.setTrailerUrl("https://trailerurl.com");
        movieTwo.setReleaseDate(LocalDate.of(1993, 6, 11));
        DirectorResponseDto directorTwo = new DirectorResponseDto();
        directorTwo.setId(2L);
        directorTwo.setName("Steven Spielberg");
        movieTwo.setDirector(directorTwo);
        movieTwo.setAverageRating(0.0);
        movieTwo.setImageUrl("https://imageurl.com");
        movieTwo.setActors(new ArrayList<>()); // Empty collection
        movieTwo.setCategories(new ArrayList<>()); // Empty collection
        movieTwo.setComments(new ArrayList<>()); // Assuming comments should be empty as well

        movieThree.setId(3L);
        movieThree.setTitle("Pulp Fiction");
        movieThree.setDuration(154);
        movieThree.setDescription("Interconnected stories");
        movieThree.setTrailerUrl("https://trailerurl.com");
        movieThree.setReleaseDate(LocalDate.of(1994, 10, 14));
        DirectorResponseDto directorThree = new DirectorResponseDto();
        directorThree.setId(1L);
        directorThree.setName("Quentin Tarantino");
        movieThree.setDirector(directorThree);
        movieThree.setAverageRating(0.0);
        movieThree.setImageUrl("https://imageurl.com");
        movieThree.setActors(new ArrayList<>()); // Empty collection
        movieThree.setCategories(new ArrayList<>()); // Empty collection
        movieThree.setComments(new ArrayList<>()); // Assuming comments should be empty as well
    }

    public static void initializeMovieUpdateDto(
            MovieUpdateDto updateDto,
            MovieResponseDto responseDto) {

        updateDto.setTitle("Updated Movie Title");
        updateDto.setDuration(160);
        updateDto.setDescription("Updated movie description");
        updateDto.setTrailerUrl("https://updatedtrailerurl.com");
        updateDto.setReleaseDate(LocalDate.of(2024, 6, 26));
        updateDto.setImageUrl("https://updatedimageurl.com");
        updateDto.setDirectorId(3L);
        updateDto.setActorIds(new ArrayList<>());
        updateDto.setCategoryIds(new ArrayList<>()); // Empty collection

        responseDto.setId(1L);
        responseDto.setTitle("Updated Movie Title");
        responseDto.setDuration(160);
        responseDto.setDescription("Updated movie description");
        responseDto.setTrailerUrl("https://updatedtrailerurl.com");
        responseDto.setReleaseDate(LocalDate.of(2024, 6, 26));
        responseDto.setAverageRating(0.0);
        responseDto.setImageUrl("https://updatedimageurl.com");

        DirectorResponseDto director = new DirectorResponseDto();
        director.setId(3L);
        director.setName("Christopher Nolan");
        director.setDescription("British-American film director, screenwriter, and producer");
        director.setImageUrl("https://someurl.com");
        responseDto.setDirector(director);

        responseDto.setActors(new ArrayList<>()); // Empty collection

        responseDto.setCategories(new ArrayList<>()); // Empty collection

        responseDto.setComments(new ArrayList<>()); // Assuming comments should be empty as well
    }
}
