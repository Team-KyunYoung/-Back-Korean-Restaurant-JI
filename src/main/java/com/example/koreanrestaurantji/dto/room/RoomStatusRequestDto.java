package com.example.koreanrestaurantji.dto.room;

import com.example.koreanrestaurantji.domain.Room;
import com.example.koreanrestaurantji.domain.RoomStatus;
import lombok.Builder;

public class RoomStatusRequestDto {
    private Room room;
    private String reservationDate;
    private String reservationTime;
    private int roomRemaining;

    @Builder
    public RoomStatusRequestDto (Room room, String reservationDate, String reservationTime, int roomRemaining) {
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
