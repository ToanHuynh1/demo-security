package com.demo_security.demo_security.controller.course;


import com.demo_security.demo_security.payload.course.CourseRequest;
import com.demo_security.demo_security.payload.course.CourseResponse;
import com.demo_security.demo_security.service.course.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course", description = "Course management endpoints")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasAuthority('COURSE_CREATE')")
    @PostMapping
    @Operation(summary = "Create new course", description = "Create a new course")
    @ApiResponse(responseCode = "200", description = "Course created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.createCourse(request));
    }

    @PreAuthorize("hasAuthority('COURSE_UPDATE')")
    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Update an existing course")
    @ApiResponse(responseCode = "200", description = "Course updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    @ApiResponse(responseCode = "404", description = "Course not found")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @PreAuthorize("hasAuthority('COURSE_DELETE')")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Delete a course")
    @ApiResponse(responseCode = "200", description = "Course deleted successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    @ApiResponse(responseCode = "404", description = "Course not found")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('COURSE_VIEW')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieve a specific course by its ID")
    @ApiResponse(responseCode = "200", description = "Course retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    @ApiResponse(responseCode = "404", description = "Course not found")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PreAuthorize("hasAuthority('COURSE_VIEW')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieve all courses")
    @ApiResponse(responseCode = "200", description = "Courses retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }
}
