package com.example.koreanrestaurantji.dto.dish;

import com.example.koreanrestaurantji.domain.DishNutritionFacts;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DishNutritionFactsResponseDto {
    private final int dishServingSize;
    private final int dishCalroies;
    private final int dishCarbohydrate;
    private final int dishSugars;
    private final int dishProtein;
    private final int dishFat;
    private final int dishTransFat;
    private final int dishCholesterol;
    private final int dishSodium;

    public DishNutritionFactsResponseDto(DishNutritionFacts dishNutritionFacts) {
        this.dishServingSize = dishNutritionFacts.getDishServingSize();
        this.dishCalroies = dishNutritionFacts.getDishCalroies();
        this.dishCarbohydrate = dishNutritionFacts.getDishCarbohydrate();
        this.dishSugars = dishNutritionFacts.getDishSugars();
        this.dishProtein = dishNutritionFacts.getDishProtein();
        this.dishFat = dishNutritionFacts.getDishFat();
        this.dishTransFat = dishNutritionFacts.getDishTransFat();
        this.dishCholesterol = dishNutritionFacts.getDishCholesterol();
        this.dishSodium = dishNutritionFacts.getDishSodium();
    }
}
