package com.example.koreanrestaurantji.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DishNutritionFactsRequestDto {
    private int dishServingSize;
    private int dishCalroies;
    private int dishCarbohydrate;
    private int dishSugars;
    private int dishProtein;
    private int dishFat;
    private int dishTransFat;
    private int dishCholesterol;
    private int dishSodium;
}
