package com.example.cinema.repository;

import com.example.cinema.model.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByNameStartingWithIgnoreCase(final String name, final Pageable pageable);

    List<Category> findByNameStartingWithIgnoreCase(final String name);
}
