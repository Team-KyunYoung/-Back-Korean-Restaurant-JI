package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Cart;
import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCartNumber(Long number);
    Optional<Cart> findByUser(User user);
}
