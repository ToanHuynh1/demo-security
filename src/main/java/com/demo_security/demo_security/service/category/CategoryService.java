package com.demo_security.demo_security.service.category;

import com.demo_security.demo_security.model.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category create(Category category);
    Category update(Long id, Category category);
    void delete(Long id);
}
