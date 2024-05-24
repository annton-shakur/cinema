package com.example.cinema.repository;

import com.example.cinema.model.Comment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByMovieId(final Pageable pageable, final Long movieId);

    @EntityGraph(attributePaths = "user")
    Optional<Comment> findById(final Long id);
}
