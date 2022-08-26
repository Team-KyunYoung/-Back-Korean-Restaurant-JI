package com.example.koreanrestaurantji.dto.Cart;

import com.example.koreanrestaurantji.domain.Cart;
import com.example.koreanrestaurantji.domain.Dish;
import com.example.koreanrestaurantji.domain.Reservation;
import com.example.koreanrestaurantji.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartCreateRequestDto {
    private User user;
    private Dish dish;
    private int cartQuantity;
    private int cartDishPrice;

    public CartCreateRequestDto(User user, Dish dish, int cartQuantity){
        this.user = user;
        this.dish = dish;
        this.cartQuantity = cartQuantity;
        this.cartDishPrice = dish.getDishPrice()*cartQuantity;
    }

    public Cart toEntity() {
        return Cart.builder()
                .user(user)
                .dish(dish)
                .cartQuantity(cartQuantity)
                .cartDishPrice(cartDishPrice)
                .build();
    }
}
