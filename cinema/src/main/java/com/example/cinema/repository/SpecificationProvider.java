package com.example.cinema.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {

    String getFieldName();

    Specification<T> getSpecification(final String[] params);
}
