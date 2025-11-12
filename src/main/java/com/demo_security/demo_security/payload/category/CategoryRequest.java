package com.demo_security.demo_security.payload.category;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CategoryRequest {
    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
    private String description;
}
