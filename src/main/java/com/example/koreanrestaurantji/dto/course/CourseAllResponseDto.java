package com.example.koreanrestaurantji.dto.course;

import com.example.koreanrestaurantji.domain.Course;
import com.example.koreanrestaurantji.dto.dish.DishNameResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourseAllResponseDto {
    private final String courseName;
    private final DishNameResponseDto appetizer;
    private final DishNameResponseDto entree1;
    private final DishNameResponseDto entree2;
    private final DishNameResponseDto entree3;
    private final DishNameResponseDto dessert;
    private final int coursePrice;

    public CourseAllResponseDto(Course course) {
        this.courseName = course.getCourseName();
        this.coursePrice = course.getCoursePrice();

        long dishNumber = course.getAppetizer().getDishNumber();
        String dishName = course.getAppetizer().getDishName();
        this.appetizer = DishNameResponseDto.builder()
                .dishNumber(dishNumber)
                .dishName(dishName)
                .build();

        dishNumber = course.getEntree1().getDishNumber();
        dishName = course.getEntree1().getDishName();
        this.entree1 = DishNameResponseDto.builder()
                .dishNumber(dishNumber)
                .dishName(dishName)
                .build();

        dishNumber = course.getEntree2().getDishNumber();
        dishName = course.getEntree2().getDishName();
        this.entree2 = DishNameResponseDto.builder()
                .dishNumber(dishNumber)
                .dishName(dishName)
                .build();

        dishNumber = course.getEntree3().getDishNumber();
        dishName = course.getEntree3().getDishName();
        this.entree3 = DishNameResponseDto.builder()
                .dishNumber(dishNumber)
                .dishName(dishName)
                .build();

        dishNumber = course.getDessert().getDishNumber();
        dishName = course.getDessert().getDishName();
        this.dessert = DishNameResponseDto.builder()
                .dishNumber(dishNumber)
                .dishName(dishName)
                .build();
    }
}
