package com.example.koreanrestaurantji.dto.dish;

import com.example.koreanrestaurantji.domain.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DishResponseDto {
    private final long dishNumber;
    private final String dishName;
    private final String dishPhoto;
    private final int dishPrice;
    private final String dishCategory;
    private final String dishDescription;
    private final DishNutritionFactsResponseDto dishNutritionFacts;

    public DishResponseDto(Dish dish) {
        this.dishNumber = dish.getDishNumber();
        this.dishName = dish.getDishName();
        this.dishPhoto = dish.getDishPhoto();
        this.dishPrice = dish.getDishPrice();
        this.dishCategory = dish.getDishCategory();
        this.dishDescription = dish.getDishDescription();
        this.dishNutritionFacts = new DishNutritionFactsResponseDto(dish.getDishNutritionFacts());
    }
}
