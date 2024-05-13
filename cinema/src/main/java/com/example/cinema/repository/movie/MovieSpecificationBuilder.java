package com.example.cinema.repository.movie;

import com.example.cinema.dto.movie.MovieSearchParameters;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.SpecificationBuilder;
import com.example.cinema.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSpecificationBuilder implements SpecificationBuilder<Movie> {
    private final SpecificationProviderManager<Movie> specificationProviderManager;

    @Override
    public Specification<Movie> build(final MovieSearchParameters searchParameters) {
        Specification<Movie> spec = Specification.where(null);

        if (searchParameters.getActors() != null && searchParameters.getActors().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("actors")
                    .getSpecification(searchParameters.getActors())
            );
        }

        if (searchParameters.getDirector() != null && searchParameters.getDirector().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("director")
                    .getSpecification(searchParameters.getDirector())
            );
        }

        if (searchParameters.getCategories() != null
                && searchParameters.getCategories().length > 0) {
            spec = spec.and(specificationProviderManager
                    .getSpecificationProvider("categories")
                    .getSpecification(searchParameters.getCategories())
            );
        }

        return spec;
    }
}
