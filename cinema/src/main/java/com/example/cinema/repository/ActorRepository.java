package com.example.cinema.repository;

import com.example.cinema.model.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Page<Actor> findByNameStartingWithIgnoreCase(String name, Pageable pageable);
}
