package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Orders;
import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNumber(Long number);
    List<Orders> findByUser(User user);
    @Query( value = "SELECT o FROM ORDERS o WHERE o.orderStatus NOT IN (:status)")
    List<Orders> findByOrderStatusNot(List<String> status);
    List<Orders> findByUserOrderByCreatedDateDesc(User user);
    List<Orders> findAllByCreatedDateBefore(LocalDateTime limitDate);

    @Transactional
    @Modifying
    @Query( value = "delete from ORDERS o where o in :orders")
    void deleteAllByDate(List<Orders> orders);
}
