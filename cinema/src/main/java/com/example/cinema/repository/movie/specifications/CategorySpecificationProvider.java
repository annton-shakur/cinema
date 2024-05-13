package com.example.cinema.repository.movie.specifications;

import com.example.cinema.model.Actor;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecificationProvider implements SpecificationProvider<Movie> {
    private static final String CATEGORY_KEY = "categories";

    @Override
    public String getFieldName() {
        return CATEGORY_KEY;
    }

    public Specification<Movie> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Movie, Actor> actorJoin = root.join(CATEGORY_KEY);
            List<String> categoryIds = Arrays.asList(params);
            return criteriaBuilder.isTrue(actorJoin.get("id").in(categoryIds));
        };
    }
}
