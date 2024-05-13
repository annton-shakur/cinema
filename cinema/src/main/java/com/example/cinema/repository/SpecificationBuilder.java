package com.example.cinema.repository;

import com.example.cinema.dto.movie.MovieSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {

    Specification<T> build(final MovieSearchParameters searchParameters);
}
