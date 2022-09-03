package com.example.koreanrestaurantji.dto.reservation;

import com.example.koreanrestaurantji.domain.Reservation;
import com.example.koreanrestaurantji.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ReservationCreateRequestDto {
    private User user;
    private String reservationRoomName;
    private String reservationName;
    private String reservationPhoneNumber;
    private LocalDate reservationDate;
    private String reservationTime;
    private String reservationHeadCount;
    private String reservationRequest;

    public ReservationCreateRequestDto(User user, ReservationRequestDto reservationRequestDto, String reservationRoomName) throws ParseException {
        this.user = user;
        this.reservationName = reservationRequestDto.getReservationName();
        this.reservationPhoneNumber = reservationRequestDto.getReservationPhoneNumber();
        this.reservationRoomName = reservationRoomName;
        this.reservationRequest = reservationRequestDto.getReservationRequest();
        LocalDate reservationDate = LocalDate.parse(reservationRequestDto.getReservationDate(), DateTimeFormatter.ISO_DATE);
        this.reservationDate = reservationDate;
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
                .reservationRequest(reservationRequest)
                .reservationRoomName(reservationRoomName)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .reservationHeadCount(reservationHeadCount)
                .build();
    }
}
