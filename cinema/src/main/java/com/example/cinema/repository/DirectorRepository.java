package com.example.cinema.repository;

import com.example.cinema.model.Director;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Page<Director> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}
