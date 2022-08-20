package com.example.koreanrestaurantji.dto.room;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoomStatusTimeRequestDto {
    private String reservationDate;
    private String reservationTime;
}
