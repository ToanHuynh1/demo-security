package com.demo_security.demo_security.payload.course;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class CourseRequest {
    @NotBlank(message = "Tên khóa học không được để trống")
    private String name;

    private String description;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải lớn hơn 0")
    private Double price;

    @NotNull(message = "CategoryId không được để trống")
    private Long categoryId;
}
