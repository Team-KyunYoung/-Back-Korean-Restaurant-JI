package com.example.koreanrestaurantji.dto.reservation;

import com.example.koreanrestaurantji.domain.Reservation;
import com.example.koreanrestaurantji.domain.User;
import com.example.koreanrestaurantji.repository.RoomRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationCreateRequestDto {
    private User user;
    private String reservationRoomName;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationDate;
    private String reservationTime;
    private String reservationHeadCount;

    public ReservationCreateRequestDto(User user, ReservationRequestDto reservationRequestDto, String reservationRoomName) {
        this.user = user;
        this.reservationName = reservationRequestDto.getReservationName();
        this.reservationPhoneNumber = reservationRequestDto.getReservationPhoneNumber();
        this.reservationRoomName = reservationRoomName;
        this.reservationDate = reservationRequestDto.getReservationDate();
        this.reservationTime = reservationRequestDto.getReservationTime();
        switch (reservationRequestDto.getReservationTableCount()) {
            case 1:
                this.reservationHeadCount = "2~4인"; break;
            case 2:
                this.reservationHeadCount = "5~8인"; break;
            case 3:
                this.reservationHeadCount = "9~12인"; break;
            default:
                this.reservationHeadCount = "비공개"; break;
        }
    }

    public Reservation toEntity() {
        return Reservation.createBuilder()
                .user(user)
                .reservationName(reservationName)
                .reservationPhoneNumber(reservationPhoneNumber)
                .reservationRoomName(reservationRoomName)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .reservationHeadCount(reservationHeadCount)
                .build();
    }
}
