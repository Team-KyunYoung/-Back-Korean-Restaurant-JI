package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Dish;
import com.example.koreanrestaurantji.domain.DishNutritionFacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishNutritionFactsRepository extends JpaRepository<DishNutritionFacts, Long> {
    List<DishNutritionFacts> findByDish(Dish dish);
}
