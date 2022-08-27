package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionBoard, Long> {
}
