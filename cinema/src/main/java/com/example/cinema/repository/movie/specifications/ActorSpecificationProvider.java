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
public class ActorSpecificationProvider implements SpecificationProvider<Movie> {
    private static final String ACTOR_KEY = "actors";

    @Override
    public String getFieldName() {
        return ACTOR_KEY;
    }

    @Override
    public Specification<Movie> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> {
            Join<Movie, Actor> actorJoin = root.join(ACTOR_KEY);
            List<String> actorIds = Arrays.asList(params);
            return criteriaBuilder.isTrue(actorJoin.get("id").in(actorIds));
        };
    }
}
