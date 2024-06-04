package com.example.cinema.repository;

import com.example.cinema.model.MovieRating;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<MovieRating, Long> {
    @EntityGraph(attributePaths = "movie")
    Optional<MovieRating> findByUserIdAndMovieId(Long userId, Long movieId);
}
