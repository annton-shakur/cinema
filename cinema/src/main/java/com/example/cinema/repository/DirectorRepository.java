package com.example.cinema.repository;

import com.example.cinema.model.Director;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    @EntityGraph(attributePaths = "movieList")
    Page<Director> findAll(final Pageable pageable);

    @EntityGraph(attributePaths = "movieList")
    Page<Director> findByNameStartingWithIgnoreCase(final String name, final Pageable pageable);

    @EntityGraph(attributePaths = "movieList")
    Optional<Director> findById(final Long id);
}
