package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Reservation;
import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByReservationNumber(Long reservationNumber);
    List<Reservation> findByUserAndReservationDateIsBeforeOrderByReservationDateDesc(User user, LocalDate now);
    List<Reservation> findByUserAndReservationDateIsGreaterThanEqualOrderByReservationDateAsc(User user, LocalDate now);
    @Transactional
    void deleteAllByReservationDateBefore(LocalDate limitDate);
}
