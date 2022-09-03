package com.example.koreanrestaurantji.dto.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {
    private long reservationRoomNumber;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationRequest;
    private String reservationDate;
    private String reservationTime;
    private int reservationTableCount;
}
