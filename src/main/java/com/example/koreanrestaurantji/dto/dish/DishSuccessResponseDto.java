package com.example.koreanrestaurantji.dto.dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class DishSuccessResponseDto {
    private HttpStatus status;
}
