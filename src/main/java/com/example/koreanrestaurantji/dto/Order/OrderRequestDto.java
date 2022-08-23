package com.example.koreanrestaurantji.dto.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private List<OrderDishDetailRequest> dishNumberList;
}