package com.example.koreanrestaurantji.dto.cart;

import com.example.koreanrestaurantji.domain.Cart;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartResponseDto {
        private long cartNumber;
        private long dishNumber;
        private String dishImage;
        private String dishName;
        private int cartQuantity;
        private int dishPrice;

    public CartResponseDto(Cart cart) {
        this.cartNumber = cart.getCartNumber();
        this.dishNumber = cart.getDish().getDishNumber();
        this.dishImage = cart.getDish().getDishPhoto();
        this.dishName = cart.getDish().getDishName();
        this.cartQuantity = cart.getCartQuantity();
        this.dishPrice = cart.getDish().getDishPrice();
    }
}
