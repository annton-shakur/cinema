package com.example.cinema.repository.movie.specifications;

import com.example.cinema.model.Movie;
import com.example.cinema.repository.SpecificationProvider;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DateSpecificationProvider implements SpecificationProvider<Movie> {
    private static final String DATE_KEY = "years";

    @Override
    public String getFieldName() {
        return DATE_KEY;
    }

    @Override
    public Specification<Movie> getSpecification(final String[] params) {
        return (root, query, criteriaBuilder) -> {
            Expression<Integer> yearExpression = criteriaBuilder
                    .function("year", Integer.class, root.get("releaseDate"));
            List<Predicate> predicates = new ArrayList<>();
            for (String param : params) {
                int year = Integer.parseInt(param);
                predicates.add(criteriaBuilder.equal(yearExpression, year));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
