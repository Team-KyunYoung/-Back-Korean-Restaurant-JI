package com.example.koreanrestaurantji.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequestDto {
    private String courseName;
    private String appetizer;
    private String entree1;
    private String entree2;
    private String entree3;
    private String dessert;
    private int coursePrice;
}
