package com.example.cinema.repository;

import com.example.cinema.model.Director;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Page<Director> findAll(final Pageable pageable);

    Page<Director> findByNameStartingWithIgnoreCase(final String name, final Pageable pageable);

    List<Director> findByNameStartingWithIgnoreCase(final String name);

    Optional<Director> findById(final Long id);

}
