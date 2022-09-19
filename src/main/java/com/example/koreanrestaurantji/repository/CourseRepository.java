package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseNumber(Long number);
    List<Course> findByCourseNameContaining(String input);
}
