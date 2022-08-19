package com.example.koreanrestaurantji.dto.course;

import com.example.koreanrestaurantji.domain.Course;
import com.example.koreanrestaurantji.domain.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseResponseDto {
    private final String courseName;
    private final Dish appetizer;
    private final Dish entree1;
    private final Dish entree2;
    private final Dish entree3;
    private final Dish dessert;

    public CourseResponseDto(Course course) {
        this.courseName = course.getCourseName();
        this.appetizer = course.getAppetizer();
        this.entree1 = course.getEntree1();
        this.entree2 = course.getEntree2();
        this.entree3 = course.getEntree3();
        this.dessert = course.getDessert();
    }
}
