package com.example.cinema.repository.movie;

import com.example.cinema.exception.SpecificationException;
import com.example.cinema.model.Movie;
import com.example.cinema.repository.SpecificationProvider;
import com.example.cinema.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieSpecificationProvider implements SpecificationProviderManager<Movie> {
    private final List<SpecificationProvider<Movie>> specificationProviders;

    @Override
    public SpecificationProvider<Movie> getSpecificationProvider(final String fieldName) {
        return specificationProviders.stream()
                .filter(sp -> fieldName.equals(sp.getFieldName()))
                .findFirst()
                .orElseThrow(() -> new SpecificationException(
                        "Can't find specification provider for field with name: " + fieldName)
                );
    }
}
