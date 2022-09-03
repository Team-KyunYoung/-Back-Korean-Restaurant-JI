package com.example.koreanrestaurantji.dto.room;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RoomStatusTimeRequestDto {
    private LocalDate reservationDate;
    private String reservationTime;
}
