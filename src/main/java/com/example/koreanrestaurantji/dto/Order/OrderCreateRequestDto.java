package com.example.koreanrestaurantji.dto.Order;

import com.example.koreanrestaurantji.domain.Orders;
import com.example.koreanrestaurantji.domain.User;

public class OrderCreateRequestDto {
    private User user;

    public OrderCreateRequestDto(User user){
        this.user = user;
    }

    public Orders toEntity() {
        return Orders.builder()
                .user(user)
                .build();
    }
}
