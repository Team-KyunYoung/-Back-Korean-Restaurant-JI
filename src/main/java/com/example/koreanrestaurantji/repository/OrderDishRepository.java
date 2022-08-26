package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Orders;
import com.example.koreanrestaurantji.domain.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
    List<OrderDish> findByOrders(Orders orders);
}
