package com.example.cinema.service.impl;

import com.example.cinema.dto.movie.MovieResponseDto;
import com.example.cinema.dto.rating.RatingCreateDto;
import com.example.cinema.exception.EntityNotFoundException;
import com.example.cinema.mapper.MovieMapper;
import com.example.cinema.model.Movie;
import com.example.cinema.model.MovieRating;
import com.example.cinema.model.User;
import com.example.cinema.repository.RatingRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.repository.movie.MovieRepository;
import com.example.cinema.service.RatingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private static final String CANNOT_FIND_MOVIE_BY_ID_MSG = "Cannot find movie by id: ";
    private static final String CANNOT_FIND_USER_BY_ID_MSG = "Cannot find user by id: ";
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MovieMapper movieMapper;
    private final RatingRepository ratingRepository;
    private final Logger logger;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public MovieResponseDto rateByMovieId(final Long movieId,
                                          final RatingCreateDto createDto,
                                          final Long userId) {
        Movie movieFromDb = movieRepository.findById(movieId).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_MOVIE_BY_ID_MSG + "{}", movieId);
                    return new EntityNotFoundException(CANNOT_FIND_MOVIE_BY_ID_MSG + movieId);
                }
        );
        logger.info("[Service]: Adding rating: {}, movieId: {}", createDto, movieId);
        User userFromDb = userRepository.findById(userId).orElseThrow(
                () -> {
                    logger.error(CANNOT_FIND_USER_BY_ID_MSG + "{}", userId);
                    return new EntityNotFoundException(CANNOT_FIND_USER_BY_ID_MSG + userId);
                }
        );
        Optional<MovieRating> movieRatingOptional = ratingRepository
                .findByUserIdAndMovieId(userId, movieId);
        MovieRating rating = movieRatingOptional.orElseGet(
                () -> new MovieRating(userFromDb, movieFromDb, createDto.getRating()));
        rating.setRating(createDto.getRating());
        ratingRepository.save(rating);
        entityManager.refresh(movieFromDb);
        double averageRating = getAverageRating(movieFromDb);
        movieFromDb.setAverageRating(averageRating);
        movieRepository.save(movieFromDb);
        return movieMapper.toDto(movieFromDb);
    }

    private double getAverageRating(Movie movieFromDb) {
        return movieFromDb.getRatings()
                .stream()
                .mapToInt(MovieRating::getRating)
                .average()
                .orElse(0.0);
    }
}
