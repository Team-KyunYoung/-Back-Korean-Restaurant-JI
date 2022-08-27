package com.example.koreanrestaurantji.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishDetailRequest {
    Long dishNumber;
    int orderQuantity;
}
