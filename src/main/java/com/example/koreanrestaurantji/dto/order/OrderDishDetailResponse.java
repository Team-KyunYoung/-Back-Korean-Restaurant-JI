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
    int orderQuantity;
    int orderDishPrice;

    public OrderDishDetailResponse(OrderDish orderDish) {
        this.dishNumber = orderDish.getDish().getDishNumber();
        this.dishName = orderDish.getDish().getDishName();
        this.orderQuantity = orderDish.getOrderQuantity();
        this.orderDishPrice = orderDish.getOrderDishPrice();
    }
}
