package com.demo_security.demo_security.payload.course;

import com.demo_security.demo_security.model.Category;
import com.demo_security.demo_security.model.Course;

public class CourseMapper {
    public static Course toEntity(CourseRequest req, Category category) {
        Course course = new Course();
        course.setName(req.getName());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        course.setCategory(category);
        return course;
    }

    public static void updateEntity(Course course, CourseRequest req, Category category) {
        course.setName(req.getName());
        course.setDescription(req.getDescription());
        course.setPrice(req.getPrice());
        course.setCategory(category);
    }

    public static CourseResponse toResponse(Course course) {
        CourseResponse resp = new CourseResponse();
        resp.setId(course.getId());
        resp.setName(course.getName());
        resp.setDescription(course.getDescription());
        resp.setPrice(course.getPrice());
        if (course.getCategory() != null) {
            resp.setCategoryId(course.getCategory().getId());
            resp.setCategoryName(course.getCategory().getName());
        }
        return resp;
    }
}
