package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.Course;
import com.example.koreanrestaurantji.domain.Dish;
import com.example.koreanrestaurantji.dto.course.*;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.CourseRepository;
import com.example.koreanrestaurantji.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final DishRepository dishRepository;

    public CourseSuccessResponseDto create(CourseCreateRequestDto courseCreateRequestDto) {
        Dish appetizer = dishRepository.findByDishName(courseCreateRequestDto.getAppetizer()).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        Dish entree1 = dishRepository.findByDishName(courseCreateRequestDto.getEntree1()).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        Dish entree2 = dishRepository.findByDishName(courseCreateRequestDto.getEntree2()).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        Dish entree3 = dishRepository.findByDishName(courseCreateRequestDto.getEntree3()).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
        Dish dessert = dishRepository.findByDishName(courseCreateRequestDto.getDessert()).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));

        System.out.println(courseCreateRequestDto.getCourseName());

        CourseRequestDto courseRequestDto = CourseRequestDto.builder()
                .courseName(courseCreateRequestDto.getCourseName())
                .appetizer(appetizer)
                .entree1(entree1)
                .entree2(entree2)
                .entree3(entree3)
                .dessert(dessert)
                .build();

        try {
            courseRepository.save(courseRequestDto.toEntity());
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_COURSE);
        }
        return new CourseSuccessResponseDto(HttpStatus.OK);
    }

    public List<CourseResponseDto> allCourse() {
        return courseRepository.findAll()
                .stream()
                .map(CourseResponseDto::new)
                .collect(Collectors.toList());
    }

    public CourseResponseDto findCourse(Long number) {
        Course course = courseRepository.findByCourseNumber(number).orElseThrow(() -> new BaseException(BaseResponseCode.COURSE_NOT_FOUND));
        return new CourseResponseDto(course);
    }

    public CourseSuccessResponseDto update(CourseUpdateRequestDto courseUpdateRequestDto) {
        Course course = courseRepository.findByCourseNumber(courseUpdateRequestDto.getCourseNumber()).orElseThrow(() -> new BaseException(BaseResponseCode.COURSE_NOT_FOUND));

        courseRepository.save(courseUpdateRequestDto.toEntity());

        return new CourseSuccessResponseDto(HttpStatus.OK);
    }

    public CourseSuccessResponseDto delete(Long number){
        Course course = courseRepository.findByCourseNumber(number).orElseThrow(() -> new BaseException(BaseResponseCode.COURSE_NOT_FOUND));
        courseRepository.delete(course);

        return new CourseSuccessResponseDto(HttpStatus.OK);
    }
}
