package com.example.cinema.repository.movie.specifications;

import com.example.cinema.model.Director;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.SpecificationProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DirectorSpecificationProvider implements SpecificationProvider<Movie> {
    private static final String DIRECTOR_KEY = "director";

    @Override
    public String getFieldName() {
        return DIRECTOR_KEY;
    }

    public Specification<Movie> getSpecification(final String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Movie, Director> directorJoin = root.join(DIRECTOR_KEY);
            List<Predicate> predicates = new ArrayList<>();
            for (String directorId : params) {
                predicates.add(criteriaBuilder.equal(directorJoin.get("id"), directorId));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
