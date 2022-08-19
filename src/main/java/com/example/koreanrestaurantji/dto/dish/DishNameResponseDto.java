package com.example.koreanrestaurantji.dto.dish;

import com.example.koreanrestaurantji.domain.Course;
import com.example.koreanrestaurantji.domain.Dish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DishNameResponseDto {
    private final long dishNumber;
    private final String dishName;

    @Builder
    public DishNameResponseDto(long dishNumber, String dishName) {
        this.dishNumber = dishNumber;
        this.dishName = dishName;
    }
}
