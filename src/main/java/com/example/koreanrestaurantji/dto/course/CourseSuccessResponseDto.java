package com.example.koreanrestaurantji.dto.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CourseSuccessResponseDto {
    private HttpStatus status;
}
