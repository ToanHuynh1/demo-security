package com.demo_security.demo_security.service.category;

import com.demo_security.demo_security.model.Category;
import com.demo_security.demo_security.payload.category.CategoryRequest;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category create(CategoryRequest request);
    Category update(Long id, CategoryRequest request);
    void delete(Long id);
}
