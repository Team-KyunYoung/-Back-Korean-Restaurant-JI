package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String email);
    Optional<User> findByUserNumber(Long userNumber);

    Optional<Boolean> existsByUserEmail(String email);
    Optional<Boolean> existsByUserNickname(String nickname);
}
