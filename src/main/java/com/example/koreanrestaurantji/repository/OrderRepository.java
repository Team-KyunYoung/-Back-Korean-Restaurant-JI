package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Orders;
import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNumber(Long number);
    List<Orders> findByUserOrderByCreatedDateDesc(User user);
}
