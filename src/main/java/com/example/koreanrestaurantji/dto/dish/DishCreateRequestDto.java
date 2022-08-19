package com.example.koreanrestaurantji.dto.dish;

import com.example.koreanrestaurantji.domain.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishCreateRequestDto {
    private String dishName;
    private String dishPhoto;
    private int dishPrice;
    private String dishCategory;
    private String dishDescription;

    public Dish toEntity() {
        return Dish.builder()
                .dishName(dishName)
                .dishPhoto(dishPhoto)
                .dishPrice(dishPrice)
                .dishCategory(dishCategory)
                .dishDescription(dishDescription)
                .build();
    }
}
