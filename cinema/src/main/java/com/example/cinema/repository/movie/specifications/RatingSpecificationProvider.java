package com.example.cinema.repository.movie.specifications;

import com.example.cinema.model.Movie;
import com.example.cinema.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class RatingSpecificationProvider implements SpecificationProvider<Movie> {
    private static final String RATING_KEY = "rating";

    @Override
    public String getFieldName() {
        return RATING_KEY;
    }

    @Override
    public Specification<Movie> getSpecification(final String[] params) {
        return (root, query, criteriaBuilder) -> {
            Double averageRating = Double.valueOf(params[0]);
            return criteriaBuilder.greaterThan(root.get("averageRating"), averageRating);
        };
    }
}
