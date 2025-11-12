package com.demo_security.demo_security.service.course;


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

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;

    public CourseServiceImpl(CourseRepository courseRepository, CategoryRepository categoryRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
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
