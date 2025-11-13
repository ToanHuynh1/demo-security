package com.demo_security.demo_security.model;
import com.demo_security.demo_security.model.Category;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private Category category;
    // Thêm các trường khác nếu cần

    public static CourseDto fromEntity(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .category(course.getCategory())
                .build();
    }
}
