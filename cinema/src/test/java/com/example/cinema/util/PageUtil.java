package com.example.cinema.util;

import com.example.cinema.extra.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {

    public static <T> Page<T> toPage(PageResponse<T> pageResponse, Pageable pageable) {
        return new PageImpl<>(
                pageResponse.getContent(),
                PageRequest.of(pageResponse.getNumber(), pageResponse.getSize()),
                pageResponse.getTotalElements());
    }
}
