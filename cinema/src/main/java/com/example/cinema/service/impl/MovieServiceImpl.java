package com.example.cinema.service.impl;

import com.example.cinema.dto.actor.ActorResponseDto;
import com.example.cinema.dto.category.CategoryResponseDto;
import com.example.cinema.dto.movie.MovieCreateDto;
import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.movie.MovieSearchParameters;
import com.example.cinema.dto.movie.MovieUpdateDto;
import com.example.cinema.dto.movie.SearchParams;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.MovieMapper;
import com.example.cinema.model.Actor;
import com.example.cinema.model.Category;
import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.movie.MovieRepository;
import com.example.cinema.repository.movie.MovieSpecificationBuilder;
import com.example.cinema.service.MovieService;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private static final String CANNOT_FIND_MOVIE_BY_ID_MSG = "Cannot find movie by id: ";
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final Logger logger;
    private final MovieSpecificationBuilder specificationBuilder;

    @Override
    @Transactional
    public Page<MovieResponseDto> searchByTitle(final Pageable pageable, final String title) {
        logger.info("[Service]: Searching movies by title: {}", title);
        Page<Movie> moviesFromDb = movieRepository
                .findByTitleStartingWithIgnoreCase(pageable, title);
        return moviesFromDb.map(movieMapper::toDto);
    }

    @Override
    public Page<MovieResponseDto> getAll(final Pageable pageable) {
        logger.info("[Service]: Getting all movies");
        Page<Movie> moviesFromDb = movieRepository.findAll(pageable);
        return moviesFromDb.map(movieMapper::toDto);
    }

    @Override
    public MovieResponseDto findById(final Long id) {
        logger.info("[Service]: Finding movie by id: {}", id);
        Movie movieFromDb = movieRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_MOVIE_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_MOVIE_BY_ID_MSG + id);
                }
        );
        return movieMapper.toDto(movieFromDb);
    }

    @Override
    public MovieResponseDto saveMovie(final MovieCreateDto createDto) {
        logger.info("[Service]: Saving movie: {}", createDto);
        Movie movie = movieMapper.toModel(createDto);
        movie.setAverageRating(0.0);
        movieRepository.save(movie);
        return movieMapper.toDto(movie);
    }

    @Override
    public MovieResponseDto updateMovie(final Long id, final MovieUpdateDto updateDto) {
        logger.info("[Service]: Updating movie with id: {}, updateDto: {}", id, updateDto);
        Movie movieFromDb = movieRepository.findById(id).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_MOVIE_BY_ID_MSG + "{}", id);
                    return new EntityNotFoundException(CANNOT_FIND_MOVIE_BY_ID_MSG + id);
                }
        );
        setUpdatedFields(movieFromDb, updateDto);
        movieRepository.save(movieFromDb);
        return movieMapper.toDto(movieFromDb);
    }

    @Override
    public void deleteById(final Long id) {
        logger.info("[Service]: Deleting movie by id: {}", id);
        movieRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<MovieResponseDto> searchMovies(final SearchParams searchParams,
                                               final Pageable pageable
    ) {
        MovieSearchParameters searchDto = new MovieSearchParameters();
        setSearchDto(searchDto, searchParams);

        Specification<Movie> specification = specificationBuilder.build(searchDto);
        Set<Long> actorIds;
        if (searchDto.getActors() != null) {
            actorIds = Arrays.stream(searchDto.getActors())
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
        } else {
            actorIds = new HashSet<>();
        }
        Set<Long> categoryIds;
        if (searchDto.getCategories() != null) {
            categoryIds = Arrays.stream(searchDto.getCategories())
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
        } else {
            categoryIds = new HashSet<>();
        }
        List<MovieResponseDto> movieResponseDtoList = movieRepository
                .findAll(specification, pageable)
                .map(movieMapper::toDto)
                .filter(dto -> new HashSet<>(
                        dto.getActors().stream().map(ActorResponseDto::getId).toList())
                        .containsAll(actorIds))
                .filter(dto -> new HashSet<>(
                        dto.getCategories().stream().map(CategoryResponseDto::getId).toList())
                        .containsAll(categoryIds))
                .toList();

        return new PageImpl<>(movieResponseDtoList, pageable, movieResponseDtoList.size());
    }

    @Override
    @Transactional
    public List<MovieResponseDto> findAllByActorId(final Long id) {
        return movieRepository.findByActorsId(id)
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }

    @Override
    public List<MovieResponseDto> findAllByDirectorId(final Long id) {
        return movieRepository.findAllByDirectorId(id)
                .stream()
                .map(movieMapper::toDto)
                .toList();
    }

    private void setUpdatedFields(final Movie movieFromDb, final MovieUpdateDto updateDto) {
        Optional.ofNullable(updateDto.getTitle()).ifPresent(movieFromDb::setTitle);
        Optional.ofNullable(updateDto.getDuration()).ifPresent(movieFromDb::setDuration);
        Optional.ofNullable(updateDto.getDescription()).ifPresent(movieFromDb::setDescription);
        Optional.ofNullable(updateDto.getTrailerUrl()).ifPresent(movieFromDb::setTrailerUrl);
        Optional.ofNullable(updateDto.getReleaseDate()).ifPresent(movieFromDb::setReleaseDate);
        Optional.ofNullable(updateDto.getDirectorId()).ifPresent(
                id -> movieFromDb.setDirector(new Director(id)));
        Optional.ofNullable(updateDto.getActorIds()).ifPresent(
                ids -> movieFromDb.setActors(ids.stream().map(Actor::new)
                        .collect(Collectors.toSet())));
        Optional.ofNullable(updateDto.getCategoryIds()).ifPresent(
                ids -> movieFromDb.setCategories(ids.stream()
                        .map(Category::new)
                        .collect(Collectors.toSet()))
        );
    }

    private void setSearchDto(MovieSearchParameters searchDto,
                              final SearchParams searchParams) {
        if (searchParams.getDirectorId() != null) {
            String[] director = Stream.of(searchParams.getDirectorId())
                    .mapToInt(Long::intValue)
                    .mapToObj(String::valueOf)
                    .toList()
                    .toArray(String[]::new);
            searchDto.setDirector(director);
        }

        if (searchParams.getRating() != null) {
            String[] rating = Stream.of(searchParams.getRating())
                    .map(String::valueOf)
                    .toList()
                    .toArray(String[]::new);
            searchDto.setRating(rating);
        }

        if (searchParams.getYear() != null) {
            String[] year = Stream.of(searchParams.getYear())
                    .map(String::valueOf)
                    .toList()
                    .toArray(String[]::new);
            searchDto.setYears(year);
        }

        if (searchParams.getCategoryIds() != null) {
            String[] categories = searchParams.getCategoryIds().stream()
                    .mapToInt(Long::intValue)
                    .mapToObj(String::valueOf)
                    .toList()
                    .toArray(String[]::new);
            searchDto.setCategories(categories);
        }

        if (searchParams.getActorIds() != null) {
            String[] actors = searchParams.getActorIds().stream()
                    .mapToInt(Long::intValue)
                    .mapToObj(String::valueOf)
                    .toList()
                    .toArray(String[]::new);
            searchDto.setActors(actors);
        }
    }
}
