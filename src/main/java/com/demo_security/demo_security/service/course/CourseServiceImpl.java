package com.demo_security.demo_security.service.course;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.demo_security.demo_security.model.Course;
import com.demo_security.demo_security.model.Category;
import com.demo_security.demo_security.payload.course.CourseRequest;
import com.demo_security.demo_security.payload.course.CourseResponse;
import com.demo_security.demo_security.payload.course.CourseMapper;
import com.demo_security.demo_security.repository.CourseRepository;
import com.demo_security.demo_security.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.demo_security.demo_security.service.common.GenericSearchService;
import com.demo_security.demo_security.payload.course.CourseSearchCriteria;
import org.springframework.data.domain.Sort;
import com.demo_security.demo_security.model.CourseDto;
import org.springframework.data.domain.PageRequest;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<CourseDto> searchCourses(CourseSearchCriteria criteria, int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Course> spec = Specification.where(null);
        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.like(root.get("name"), "%" + criteria.getName() + "%"));
        }
        if (criteria.getCategory() != null && !criteria.getCategory().isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category").get("name"), criteria.getCategory()));
        }
        return GenericSearchService.search((JpaSpecificationExecutor<Course>) courseRepository, spec, pageable, CourseDto::fromEntity);
    }

    @Override
    public Page<Course> getCourses(Pageable pageable) {
        return courseRepository.findAllWithCategory(pageable);
    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Course course = CourseMapper.toEntity(request, category);
        Course saved = courseRepository.save(course);
        return CourseMapper.toResponse(saved);
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        CourseMapper.updateEntity(course, request, category);
        Course updated = courseRepository.save(course);
        return CourseMapper.toResponse(updated);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return CourseMapper.toResponse(course);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
