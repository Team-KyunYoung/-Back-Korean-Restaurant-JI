package com.example.koreanrestaurantji.dto.room;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RoomStatusDateRequestDto {
    private LocalDate reservationDate;
}
