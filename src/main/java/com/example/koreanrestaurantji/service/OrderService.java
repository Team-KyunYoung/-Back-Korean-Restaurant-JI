package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.*;
import com.example.koreanrestaurantji.dto.Order.*;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDishRepository orderDishRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;

    public User findUserByToken(){
        return userRepository.findByUserEmail(SecurityContextHolder.getContext()
                        .getAuthentication().getName())
                .orElseThrow(() -> new BaseException(BaseResponseCode.USER_NOT_FOUND));
    }

    public Dish findDishByDishNumber(long dishNumber){
        return dishRepository.findByDishNumber(dishNumber).orElseThrow(() -> new BaseException(BaseResponseCode.DISH_NOT_FOUND));
    }

    public SuccessResponseDto create(OrderRequestDto orderRequestDto) {
        User user = findUserByToken();

        Orders orders;
        try {
            orders = orderRepository.save(new Orders(user));
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ORDER);
        }

        try {
            for(OrderDishDetailRequest orderDishDetailRequest : orderRequestDto.getDishOrderList()){
                orderDishRepository.save(new OrderDish(orders,
                        findDishByDishNumber(orderDishDetailRequest.getDishNumber()),
                        orderDishDetailRequest.getOrderQuantity()));
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ORDER_DISH);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<OrderResponseDto> findOrderByUser() {
        User user = findUserByToken();
        List<Orders> userOrdersList = orderRepository.findByUser(user);
        if(userOrdersList == null || userOrdersList.size() == 0)
            throw new BaseException(BaseResponseCode.ORDER_NOT_FOUND);

        List<OrderResponseDto> responseDtoList = new ArrayList<>();
        for(Orders userOrders : userOrdersList) {
            List<OrderDishDetailResponse> orderDishList = orderDishRepository.findByOrders(userOrders)
                    .stream()
                    .map(OrderDishDetailResponse::new)
                    .collect(Collectors.toList());
            responseDtoList.add(new OrderResponseDto(userOrders, orderDishList));
        }
        return responseDtoList;
    }

    public SuccessResponseDto update(long orderNumber, String orderStatus){
        Orders orders = orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new BaseException(BaseResponseCode.ORDER_NOT_FOUND));

        try {
            orders.setOrderStatus(orderStatus);
            orderRepository.save(orders);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ORDER);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto delete(long orderNumber){
        Orders orders = orderRepository.findByOrderNumber(orderNumber).orElseThrow(() -> new BaseException(BaseResponseCode.ORDER_NOT_FOUND));

        try {
            orderRepository.delete(orders);
            for(OrderDish orderDish : orderDishRepository.findByOrders(orders)) {
                orderDishRepository.delete(orderDish);
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.BAD_REQUEST);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }
}
