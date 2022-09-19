package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Optional<Dish> findByDishNumber(Long number);
    Optional<Dish> findByDishName(String dishName);
    List<Dish>findByDishNameContaining(String input);
}
