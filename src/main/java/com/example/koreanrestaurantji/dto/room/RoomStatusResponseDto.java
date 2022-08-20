package com.example.koreanrestaurantji.dto.room;

import lombok.*;

@Getter
@NoArgsConstructor
public class RoomStatusResponseDto {
    private String reservationDate;
    private String reservationTime;
    private int roomRemaining;

    @Builder
    public RoomStatusResponseDto(String reservationDate, String reservationTime, int roomRemaining) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.roomRemaining = roomRemaining;
    }
}
