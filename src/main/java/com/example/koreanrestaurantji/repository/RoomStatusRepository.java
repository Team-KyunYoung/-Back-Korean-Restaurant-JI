package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {
    List<RoomStatus> findByRoom(Room room);
    List<RoomStatus> findByRoomAndReservationDate(Room room, LocalDate reservationDate);
    Optional<RoomStatus> findByRoomAndReservationDateAndReservationTime(Room room, LocalDate reservationDate, String reservationTime);
    Boolean existsByRoomAndReservationDateAndReservationTime(Room room, LocalDate reservationDate, String reservationTime);
    @Transactional
    void deleteAllByReservationDateBefore(LocalDate now);
}
