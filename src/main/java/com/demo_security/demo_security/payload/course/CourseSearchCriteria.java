package com.demo_security.demo_security.payload.course;

public class CourseSearchCriteria {
    private String name;
    private String category;
    // Thêm các trường filter khác nếu cần

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
