
package com.demo_security.demo_security.service.course;
import com.demo_security.demo_security.model.Course;

import com.demo_security.demo_security.payload.course.CourseRequest;
import com.demo_security.demo_security.payload.course.CourseResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
    CourseResponse getCourseById(Long id);
    List<CourseResponse> getAllCourses();
    Page<Course> getCourses(Pageable pageable);
}
