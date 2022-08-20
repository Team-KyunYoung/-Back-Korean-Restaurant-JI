package com.example.koreanrestaurantji.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ReservationSuccessResponseDto {
    private HttpStatus status;
}
