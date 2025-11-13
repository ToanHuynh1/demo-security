package com.demo_security.demo_security.service.category;

import com.demo_security.demo_security.model.Category;
import com.demo_security.demo_security.repository.CategoryRepository;
import com.demo_security.demo_security.payload.category.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.demo_security.demo_security.service.common.GenericSearchService;
import com.demo_security.demo_security.payload.category.CategorySearchCriteria;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Sort;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> searchCategories(CategorySearchCriteria criteria, int page, int size, Sort sort) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size, sort);
        Specification<Category> spec = Specification.where(null);
        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("name"), "%" + criteria.getName() + "%"));
        }
        // Thêm các điều kiện filter khác nếu cần
        return GenericSearchService.search((JpaSpecificationExecutor<Category>) categoryRepository, spec, pageable, c -> c);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category create(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        return categoryRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
