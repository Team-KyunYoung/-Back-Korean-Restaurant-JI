package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import lombok.Builder;

import java.time.LocalDate;

public class RoomStatusRequestDto {
    private Room room;
    private LocalDate reservationDate;
    private String reservationTime;
    private int roomRemaining;

    @Builder
    public RoomStatusRequestDto (Room room, LocalDate reservationDate, String reservationTime, int roomRemaining) {
        this.room = room;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.roomRemaining = roomRemaining;
    }

    public RoomStatus toEntity() {
        return RoomStatus.builder()
                .room(room)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .roomRemaining(roomRemaining)
                .build();
    }
}
