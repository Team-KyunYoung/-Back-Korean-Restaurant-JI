package com.example.koreanrestaurantji.dto.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class RoomSuccessResponseDto {
    private HttpStatus status;
}
