package com.example.koreanrestaurantji.dto.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {
    private long dishNumber;
    private int cartQuantity;
}
