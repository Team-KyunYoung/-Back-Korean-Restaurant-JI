package com.example.koreanrestaurantji.service;

import com.example.koreanrestaurantji.domain.*;
import com.example.koreanrestaurantji.dto.order.*;
import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.exception.BaseException;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDishRepository orderDishRepository;
    private final CartRepository cartRepository;
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

    public SuccessResponseDto createOrderInCart(OrderRequestDto orderRequestDto) {
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

                try {
                    cartRepository.delete(cartRepository.findByDish(findDishByDishNumber(orderDishDetailRequest.getDishNumber()))
                            .orElseThrow(() -> new BaseException(BaseResponseCode.CART_NOT_FOUND)));

                } catch (Exception e) {
                    throw new BaseException(BaseResponseCode.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.FAILED_TO_SAVE_ORDER_DISH);
        }

        return new SuccessResponseDto(HttpStatus.OK);
    }

    public List<OrderResponseDto> findOrderByStatus() {
        List<OrderResponseDto> responseDtoList = new ArrayList<>();
        if(findUserByToken().isRole()) {
            List<Orders> userOrdersList = orderRepository.findByOrderStatusNot(Arrays.asList("수령완료", "주문취소"));

            for(Orders userOrders : userOrdersList) {
                List<OrderDishDetailResponse> orderDishList = orderDishRepository.findByOrders(userOrders)
                        .stream()
                        .map(OrderDishDetailResponse::new)
                        .collect(Collectors.toList());
                responseDtoList.add(new OrderResponseDto(userOrders, orderDishList));
            }
        }
        return responseDtoList;
    }

    public List<OrderResponseDto> findOrderByUser() {
        User user = findUserByToken();
        List<Orders> userOrdersList = orderRepository.findByUserOrderByCreatedDateDesc(user);
//        if(userOrdersList == null || userOrdersList.size() == 0)
//            throw new BaseException(BaseResponseCode.ORDER_NOT_FOUND);

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

            //현재 삭제하려고 하는 테이블 또는 행이 다른 곳에서 참조하고 있기 때문에 에러가 날 수 있으므로,
            //참조하는 데이터 먼저 삭제하도록 한다.
            for(OrderDish orderDish : orderDishRepository.findByOrders(orders)) {
                orderDishRepository.delete(orderDish);
            }
            orderRepository.delete(orders);
        } catch (Exception e) {
            throw new BaseException(BaseResponseCode.BAD_REQUEST);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }

    public SuccessResponseDto deleteBeforeDate(){
        if(findUserByToken().isRole()) {
            LocalDateTime limitDate = LocalDateTime.now().minusMonths(6);
            List<Orders> orders = orderRepository.findAllByCreatedDateBefore(limitDate);
            try {
                orderDishRepository.deleteAllByDate(orders);
                orderRepository.deleteAllByDate(orders);
            } catch (Exception e) {
                throw new BaseException(BaseResponseCode.BAD_REQUEST);
            }
        } else {
            throw new BaseException(BaseResponseCode.METHOD_NOT_ALLOWED);
        }
        return new SuccessResponseDto(HttpStatus.OK);
    }
}
