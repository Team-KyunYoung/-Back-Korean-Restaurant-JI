package com.example.koreanrestaurantji.dto.order;

import com.example.koreanrestaurantji.domain.OrderDish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishDetailResponse {
    Long dishNumber;
    String dishName;
    String dishImg;
    int orderQuantity;
    int orderDishPrice;

    public OrderDishDetailResponse(OrderDish orderDish) {
        this.dishNumber = orderDish.getDish().getDishNumber();
        this.dishName = orderDish.getDish().getDishName();
        this.dishImg = orderDish.getDish().getDishPhoto();
        this.orderQuantity = orderDish.getOrderQuantity();
        this.orderDishPrice = orderDish.getOrderDishPrice();
    }
}
