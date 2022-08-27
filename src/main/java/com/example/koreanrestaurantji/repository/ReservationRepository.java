package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Reservation;
import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserOrderByReservationDateAsc(User user);
    Optional<Reservation> findByReservationNumber(Long reservationNumber);
}
