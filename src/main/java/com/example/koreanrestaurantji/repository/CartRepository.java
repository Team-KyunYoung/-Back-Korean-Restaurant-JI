package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Cart;
import com.example.koreanrestaurantji.domain.Dish;
import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Boolean existsByUserAndDish(User user, Dish dish);
    Optional<Cart> findByUserAndDish(User user, Dish dish);
    Optional<Cart> findByCartNumber(Long number);
    List<Cart> findByUser(User user);

    Optional<Cart> findByDish(Dish dish);
}
