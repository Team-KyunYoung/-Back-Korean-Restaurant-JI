package com.example.koreanrestaurantji.dto.course;

import com.example.koreanrestaurantji.domain.Course;
import com.example.koreanrestaurantji.domain.Dish;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CourseRequestDto {
    private String courseName;
    private Dish appetizer;
    private Dish entree1;
    private Dish entree2;
    private Dish entree3;
    private Dish dessert;

    @Builder
    public CourseRequestDto(String courseName, Dish appetizer, Dish entree1, Dish entree2, Dish entree3, Dish dessert) {
        this.courseName = courseName;
        this.appetizer = appetizer;
        this.entree1 = entree1;
        this.entree2 = entree2;
        this.entree3 = entree3;
        this.dessert = dessert;
    }

    public Course toEntity() {
        return Course.builder()
                .courseName(courseName)
                .appetizer(appetizer)
                .entree1(entree1)
                .entree2(entree2)
                .entree3(entree3)
                .dessert(dessert)
                .build();
    }
}
