package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(long roomNumber);
    Optional<Room> findByRoomName(String roomName);
    List<Room> findByRoomNameContaining(String input);
}
