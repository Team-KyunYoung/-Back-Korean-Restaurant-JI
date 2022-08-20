package com.example.koreanrestaurantji.dto.reservation;

import com.example.koreanrestaurantji.domain.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationResponseDto {
    private long reservationNumber;
    private String reservationRoomName;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationDate;
    private String reservationTime;
    private String reservationHeadCount;

    public ReservationResponseDto(Reservation reservation){
        this.reservationNumber = reservation.getReservationNumber();
        this.reservationRoomName = reservation.getReservationRoomName();
        this.reservationName = reservation.getReservationName();
        this.reservationPhoneNumber = reservation.getReservationPhoneNumber();
        this.reservationDate = reservation.getReservationDate();
        this.reservationTime = reservation.getReservationTime();
        this.reservationHeadCount = reservation.getReservationHeadCount();
    }
}
