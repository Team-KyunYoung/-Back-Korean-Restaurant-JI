package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomStatusRepository extends JpaRepository<RoomStatus, Long> {
    List<RoomStatus> findByRoom(Room room);
    List<RoomStatus> findByRoomAndReservationDate(Room room, String reservationDate);
    Optional<RoomStatus> findByRoomAndReservationDateAndReservationTime(Room room, String reservationDate, String reservationTime);
}
