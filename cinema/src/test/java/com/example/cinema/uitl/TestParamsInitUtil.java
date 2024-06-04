package com.example.cinema.uitl;

import com.example.cinema.dto.actor.ActorCreateDto;
import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.actor.ActorUpdateDto;
import com.example.cinema.dto.category.CategoryCreateDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.category.CategoryUpdateDto;
import com.example.cinema.dto.comment.CommentCreateDto;
import com.example.cinema.dto.comment.CommentResponseDto;
import com.example.cinema.dto.comment.CommentUpdateDto;
import com.example.cinema.dto.director.DirectorCreateDto;
import com.example.cinema.dto.director.DirectorResponseDto;
import com.example.cinema.dto.director.DirectorUpdateDto;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.dto.user.UserRegistrationRequestDto;
import com.example.cinema.dto.user.UserResponseDto;
import com.example.cinema.dto.user.UserRoleUpdateRequestDto;
import com.example.cinema.dto.user.UserWithRolesResponseDto;
import com.example.cinema.model.Actor;
import com.example.cinema.model.Category;
import com.example.cinema.model.Comment;
import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import com.example.cinema.model.Role;
import com.example.cinema.model.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TestParamsInitUtil {

    public TestParamsInitUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static void initializeActorModels(
            Actor actorOne,
            Actor actorTwo,
            Actor actorThree,
            Actor actorOneWithoutId) {
        actorOne.setId(1L);
        actorOne.setName("Leonardo DiCaprio");
        actorOne.setAge(48);
        actorOne.setDescription(
                "An actor known for his roles in Titanic, Inception, and The Revenant.");
        actorOne.setDeleted(false);

        actorTwo.setId(2L);
        actorTwo.setName("Anne Hathaway");
        actorTwo.setAge(40);
        actorTwo.setDescription(
                "An actress known for her roles in The Devil Wears Prada and Interstellar.");
        actorTwo.setDeleted(false);

        actorThree.setId(3L);
        actorThree.setName("Leonard Nimoy");
        actorThree.setAge(83);
        actorThree.setDescription("An actor known for his role as Spock in the Star Trek series.");
        actorThree.setDeleted(false);

        actorOneWithoutId.setName("Leonardo DiCaprio");
        actorOneWithoutId.setAge(48);
        actorOneWithoutId.setDescription(
                "An actor known for his roles in Titanic, Inception, and The Revenant.");
    }

    public static void initializeActorDtos(
            ActorCreateDto actorOneCreateDto,
            ActorResponseDto actorOneResponseDto,
            ActorResponseDto actorTwoResponseDto,
            ActorResponseDto actorThreeResponseDto,
            ActorUpdateDto actorOneUpdateDto,
            ActorResponseDto updatedActorOneResponseDto) {
        actorOneCreateDto.setName("Leonardo DiCaprio");
        actorOneCreateDto.setAge(48);
        actorOneCreateDto.setDescription(
                "An actor known for his roles in Titanic, Inception, and The Revenant.");

        actorOneResponseDto.setId(1L);
        actorOneResponseDto.setName("Leonardo DiCaprio");
        actorOneResponseDto.setAge(48);
        actorOneResponseDto.setDescription(
                "An actor known for his roles in Titanic, Inception, and The Revenant.");

        actorTwoResponseDto.setId(2L);
        actorTwoResponseDto.setName("Anne Hathaway");
        actorTwoResponseDto.setAge(40);
        actorTwoResponseDto.setDescription(
                "An actress known for her roles in The Devil Wears Prada and Interstellar.");

        actorThreeResponseDto.setId(3L);
        actorThreeResponseDto.setName("Leonard Nimoy");
        actorThreeResponseDto.setAge(83);
        actorThreeResponseDto.setDescription(
                "An actor known for his role as Spock in the Star Trek series.");

        actorOneUpdateDto.setName("Leonardo Wilhelm DiCaprio");
        actorOneUpdateDto.setAge(49);
        actorOneUpdateDto.setDescription(
                "An actor known for his updated roles and environmental activism.");

        updatedActorOneResponseDto.setId(1L);
        updatedActorOneResponseDto.setName("Leonardo Wilhelm DiCaprio");
        updatedActorOneResponseDto.setAge(49);
        updatedActorOneResponseDto.setDescription(
                "An actor known for his updated roles and environmental activism.");
    }

    public static void initializeCategoryModels(
            Category categoryOne,
            Category categoryTwo,
            Category categoryThree,
            Category categoryOneWithoutId) {
        categoryOne.setId(1L);
        categoryOne.setName("Detective");
        categoryOne.setDeleted(false);

        categoryTwo.setId(2L);
        categoryTwo.setName("Comedy");
        categoryTwo.setDeleted(false);

        categoryThree.setId(3L);
        categoryThree.setName("Detective Thriller");
        categoryThree.setDeleted(false);

        categoryOneWithoutId.setName("Detective");
    }

    public static void initializeCategoryDtos(
            CategoryCreateDto categoryOneCreateDto,
            CategoryResponseDto categoryOneResponseDto,
            CategoryResponseDto categoryTwoResponseDto,
            CategoryResponseDto categoryThreeResponseDto,
            CategoryUpdateDto categoryOneUpdateDto,
            CategoryResponseDto updatedCategoryOneResponseDto) {
        categoryOneCreateDto.setName("Detective");

        categoryOneResponseDto.setId(1L);
        categoryOneResponseDto.setName("Detective");

        categoryTwoResponseDto.setId(2L);
        categoryTwoResponseDto.setName("Comedy");

        categoryThreeResponseDto.setId(3L);
        categoryThreeResponseDto.setName("Detective Thriller");

        categoryOneUpdateDto.setName("Detective Updated");

        updatedCategoryOneResponseDto.setId(1L);
        updatedCategoryOneResponseDto.setName("Detective Updated");
    }

    public static void initializeAllCommentsFields(
            User user,
            Role moderatorRole,
            Comment comment,
            CommentCreateDto commentCreateDto,
            CommentUpdateDto commentUpdateDto,
            CommentResponseDto commentResponseDto) {
        Long validUserId = 1L;

        user.setId(validUserId);

        moderatorRole.setId(1L);
        moderatorRole.setRoleName(Role.RoleName.MODERATOR);

        comment.setId(1L);
        comment.setMovie(new Movie(1L, "Sample Movie", 120, "Sample Description", "http://example.com", LocalDateTime.now().toLocalDate(), null, Set.of(), Set.of(), List.of(), List.of(), 1.1, false));
        comment.setContent("Sample comment");
        comment.setCreationTime(LocalDateTime.now());
        comment.setUser(user);
        comment.setDeleted(false);

        commentCreateDto.setMovieId(1L);
        commentCreateDto.setContent("Sample comment");

        commentUpdateDto.setContent("Updated comment");

        commentResponseDto.setId(1L);
        commentResponseDto.setMovieId(1L);
        commentResponseDto.setCreationTime(comment.getCreationTime());
        commentResponseDto.setUserId(validUserId);
        commentResponseDto.setContent("Sample comment");
    }

    public static void initializeDirectorModels(
            Director directorTarantino,
            Director directorNolan,
            Director directorTarantinoWithoutId) {
        directorTarantino.setId(1L);
        directorTarantino.setName("Quentin Tarantino");
        directorTarantino.setDescription("Famous director known for his unique style");
        directorTarantino.setDeleted(false);

        directorNolan.setId(2L);
        directorNolan.setName("Christopher Nolan");
        directorNolan.setDescription(
                "Renowned director known for his complex storytelling");
        directorNolan.setDeleted(false);

        directorTarantinoWithoutId.setName("Quentin Tarantino");
        directorTarantinoWithoutId.setDescription("Famous director known for his unique style");

        Movie pulpFiction = new Movie(1L, "Pulp Fiction", 154, "Crime film", "http://example.com/pulpfiction", LocalDate.of(1994, 10, 14), directorTarantino, Set.of(), Set.of(), List.of(), List.of(),1.1,false);
        Movie killBill = new Movie(2L, "Kill Bill: Vol. 1", 111, "Martial arts film", "http://example.com/killbill", LocalDate.of(2003, 10, 10), directorTarantino, Set.of(), Set.of(), List.of(), List.of(), 1.1, false);
        Movie inception = new Movie(3L, "Inception", 148, "Science fiction film", "http://example.com/inception", LocalDate.of(2010, 7, 16), directorNolan, Set.of(), Set.of(), List.of(), List.of(), 1.1, false);
        Movie theDarkKnight = new Movie(4L, "The Dark Knight", 152, "Superhero film", "http://example.com/thedarkknight", LocalDate.of(2008, 7, 18), directorNolan, Set.of(), Set.of(), List.of(), List.of(), 1.1, false);
        List<Movie> tarantinoMovies = List.of(pulpFiction, killBill);
        List<Movie> nolanMovies = List.of(inception, theDarkKnight);

        directorTarantino.setMovieList(tarantinoMovies);
        directorNolan.setMovieList(nolanMovies);
    }

    public static void initializeDirectorDtos(
            DirectorCreateDto directorTarantinoCreateDto,
            DirectorResponseDto directorTarantinoResponseDto,
            DirectorResponseDto directorNolanResponseDto,
            DirectorUpdateDto directorTarantinoUpdateDto,
            DirectorResponseDto updatedDirectorTarantinoResponseDto) {
        directorTarantinoCreateDto.setName("Quentin Tarantino");
        directorTarantinoCreateDto.setDescription("Famous director known for his unique style");

        directorTarantinoResponseDto.setId(1L);
        directorTarantinoResponseDto.setName("Quentin Tarantino");
        directorTarantinoResponseDto.setDescription("Famous director known for his unique style");

        directorNolanResponseDto.setId(2L);
        directorNolanResponseDto.setName("Christopher Nolan");
        directorNolanResponseDto.setDescription(
                "Renowned director known for his complex storytelling");

        directorTarantinoUpdateDto.setName("Quentin Tarantino Updated");
        directorTarantinoUpdateDto.setDescription("Updated description");

        updatedDirectorTarantinoResponseDto.setId(1L);
        updatedDirectorTarantinoResponseDto.setName("Quentin Tarantino Updated");
        updatedDirectorTarantinoResponseDto.setDescription("Updated description");

        directorTarantinoResponseDto.setMovieIds(List.of(1L, 2L));
        directorNolanResponseDto.setMovieIds(List.of(3L, 4L));
        directorTarantinoUpdateDto.setMovieIds(List.of(1L));
        updatedDirectorTarantinoResponseDto.setMovieIds(List.of(1L));
    }

    public static void initializeAllMovieFields(
            Movie movie,
            MovieCreateDto movieCreateDto,
            MovieUpdateDto movieUpdateDto,
            MovieResponseDto movieResponseDto,
            MovieResponseDto updatedMovieResponseDto) {
        Director director = new Director();
        director.setId(1L);
        director.setName("Christopher Nolan");
        director.setDescription(
                "A critically acclaimed director known for Inception and The Dark Knight.");
        director.setMovieList(List.of());
        director.setDeleted(false);

        final Set<Actor> actors = Set.of(
                new Actor(1L, "Leonardo DiCaprio", 46,
                        "An American actor and film producer.", false),
                new Actor(2L, "Joseph Gordon-Levitt", 40,
                        "An American actor, filmmaker, singer, and entrepreneur.", false)
        );

        final Set<Category> categories = Set.of(
                new Category(1L, "Action", false),
                new Category(2L, "Sci-Fi", false)
        );

        User user = new User();
        user.setId(1L);
        user.setEmail("testUser@gmail.com");
        user.setPassword("testPassword");
        user.setRoles(Set.of());

        final List<Comment> comments = List.of(
                new Comment(1L, null, "Great movie!",
                        LocalDateTime.now(), user, false),
                new Comment(2L, null, "Amazing visuals and storyline.",
                        LocalDateTime.now(), user, false)
        );

        movie.setId(1L);
        movie.setTitle("Inception");
        movie.setDuration(148);
        movie.setDescription("""
                A thief who steals corporate secrets through the use of dream-sharing
                technology is given the inverse task of planting
                an idea into the mind of a C.E.O.
                """);
        movie.setTrailerUrl("http://example.com/inception_trailer");
        movie.setReleaseDate(LocalDate.of(2010, 7, 16));
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setCategories(categories);
        movie.setComments(comments);
        movie.setDeleted(false);

        movieCreateDto.setTitle("Inception");
        movieCreateDto.setDuration(148);
        movieCreateDto.setDescription("""
                A thief who steals corporate secrets through the use of dream-sharing
                technology is given the inverse task of planting
                an idea into the mind of a C.E.O.
                """);
        movieCreateDto.setTrailerUrl("http://example.com/inception_trailer");
        movieCreateDto.setReleaseDate(LocalDate.of(2010, 7, 16));
        movieCreateDto.setDirectorId(1L);
        movieCreateDto.setActorIds(List.of(1L, 2L));
        movieCreateDto.setCategoryIds(List.of(1L, 2L));

        movieUpdateDto.setTitle("Inception: Director's Cut");
        movieUpdateDto.setDuration(152);
        movieUpdateDto.setDescription("""
                A thief who steals corporate secrets through the use of dream-sharing
                technology is given the inverse task of planting an idea into the mind
                of a C.E.O. (Director's Cut)""");
        movieUpdateDto.setTrailerUrl("http://example.com/inception_directors_cut_trailer");
        movieUpdateDto.setReleaseDate(LocalDate.of(2021, 7, 16));
        movieUpdateDto.setDirectorId(1L);
        movieUpdateDto.setActorIds(List.of(1L, 2L));
        movieUpdateDto.setCategoryIds(List.of(1L, 2L));

        movieResponseDto.setId(1L);
        movieResponseDto.setTitle("Inception");
        movieResponseDto.setDuration(148);
        movieResponseDto.setDescription("""
                A thief who steals corporate secrets through the use of dream-sharing
                technology is given the inverse task of planting
                an idea into the mind of a C.E.O.
                """);
        movieResponseDto.setTrailerUrl("http://example.com/inception_trailer");
        movieResponseDto.setReleaseDate(LocalDate.of(2010, 7, 16));
        movieResponseDto.setDirectorId(1L);
        movieResponseDto.setActorIds(List.of(1L, 2L));
        movieResponseDto.setCategoryIds(List.of(1L, 2L));
        movieResponseDto.setCommentIds(List.of(1L, 2L));

        updatedMovieResponseDto.setId(1L);
        updatedMovieResponseDto.setTitle("Inception: Director's Cut");
        updatedMovieResponseDto.setDuration(152);
        updatedMovieResponseDto.setDescription("""
                A thief who steals corporate secrets through the use of dream-sharing
                technology is given the inverse task of planting an idea into the mind
                of a C.E.O. (Director's Cut)
                """);
        updatedMovieResponseDto.setTrailerUrl("http://example.com/inception_directors_cut_trailer");
        updatedMovieResponseDto.setReleaseDate(LocalDate.of(2021, 7, 16));
        updatedMovieResponseDto.setDirectorId(1L);
        updatedMovieResponseDto.setActorIds(List.of(1L, 2L));
        updatedMovieResponseDto.setCategoryIds(List.of(1L, 2L));
        updatedMovieResponseDto.setCommentIds(List.of());

    }

    public static void initializeAllUserFields(
            User user,
            Role userRole,
            UserResponseDto userResponseDto,
            UserRegistrationRequestDto userRegistrationRequestDto,
            UserRoleUpdateRequestDto userRoleUpdateRequestDto,
            UserWithRolesResponseDto userWithRolesResponseDto) {
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");

        userRole.setId(1L);
        userRole.setRoleName(Role.RoleName.USER);
        user.setRoles(Set.of(userRole));

        userRegistrationRequestDto.setEmail("test@example.com");
        userRegistrationRequestDto.setPassword("password");
        userRegistrationRequestDto.setRepeatPassword("password");
        userRegistrationRequestDto.setFirstName("John");
        userRegistrationRequestDto.setLastName("Doe");

        userRoleUpdateRequestDto.setRoleNames(Set.of(Role.RoleName.USER));

        userResponseDto.setId(1L);
        userResponseDto.setEmail("test@example.com");
        userResponseDto.setFirstName("John");
        userResponseDto.setLastName("Doe");

        userWithRolesResponseDto.setId(1L);
        userWithRolesResponseDto.setEmail("test@example.com");
        userWithRolesResponseDto.setFirstName("John");
        userWithRolesResponseDto.setLastName("Doe");
        userWithRolesResponseDto.setRoleNames(Set.of(Role.RoleName.USER));
    }
}
