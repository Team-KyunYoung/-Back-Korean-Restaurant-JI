package com.example.koreanrestaurantji.dto.course;

import com.example.koreanrestaurantji.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseSearchResponseDto {
    private final long courseNumber;
    private final String courseName;

    public CourseSearchResponseDto(Course course) {
        this.courseNumber = course.getCourseNumber();
        this.courseName = course.getCourseName();
    }
}
