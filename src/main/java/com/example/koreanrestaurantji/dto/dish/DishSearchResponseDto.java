package com.example.koreanrestaurantji.dto.dish;

import com.example.koreanrestaurantji.domain.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DishSearchResponseDto {
    private final long dishNumber;
    private final String dishName;

    public DishSearchResponseDto(Dish dish) {
        this.dishNumber = dish.getDishNumber();
        this.dishName = dish.getDishName();
    }
}
