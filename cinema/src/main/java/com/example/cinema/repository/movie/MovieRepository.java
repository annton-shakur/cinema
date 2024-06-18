package com.example.cinema.repository.movie;

import com.example.cinema.model.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository
        extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    @EntityGraph(attributePaths = {"director", "actors", "categories", "comments"})
    Page<Movie> findByTitleStartingWithIgnoreCase(final Pageable pageable, final String title);

    @EntityGraph(attributePaths = {"director", "actors", "categories", "comments"})
    Optional<Movie> findById(final Long id);

    @EntityGraph(attributePaths = {"director", "actors", "categories", "comments"})
    Page<Movie> findAll(final Pageable pageable);

    @EntityGraph(attributePaths = "director")
    List<Movie> findByActorsId(final Long actorId);

    @EntityGraph(attributePaths = {"director", "actors", "categories", "comments"})
    List<Movie> findAllByDirectorId(final Long id);
}
