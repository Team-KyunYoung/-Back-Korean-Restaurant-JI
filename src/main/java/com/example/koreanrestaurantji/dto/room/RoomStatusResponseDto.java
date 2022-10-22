package com.example.koreanrestaurantji.dto.room;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RoomStatusResponseDto {
    private LocalDate reservationDate;
    private String reservationTime;
    private int roomRemaining;

    @Builder
    public RoomStatusResponseDto(LocalDate reservationDate, String reservationTime, int roomRemaining) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.roomRemaining = roomRemaining;
    }
}
