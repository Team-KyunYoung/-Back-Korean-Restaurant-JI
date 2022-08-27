package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.EventBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<EventBoard, Long> {
}
