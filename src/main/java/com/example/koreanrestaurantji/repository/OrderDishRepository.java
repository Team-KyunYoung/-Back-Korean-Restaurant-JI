package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Orders;
import com.example.koreanrestaurantji.domain.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {
    List<OrderDish> findByOrders(Orders orders);

    @Transactional
    @Modifying
    @Query( value = "delete from ORDER_DISH o where o.orders in :orders")
    void deleteAllByDate(List<Orders> orders);
}
