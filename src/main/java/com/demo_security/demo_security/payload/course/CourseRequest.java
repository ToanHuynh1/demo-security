package com.demo_security.demo_security.payload.course;

import lombok.Data;

@Data
public class CourseRequest {
    private String name;
    private String description;
    private Double price;
    private Long categoryId;
}
