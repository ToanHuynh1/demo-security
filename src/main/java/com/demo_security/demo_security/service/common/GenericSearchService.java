package com.demo_security.demo_security.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public class GenericSearchService {
    public static <T, D> Page<D> search(JpaSpecificationExecutor<T> repository, Specification<T> spec, Pageable pageable, java.util.function.Function<T, D> mapper) {
        return repository.findAll(spec, pageable).map(mapper);
    }
}
