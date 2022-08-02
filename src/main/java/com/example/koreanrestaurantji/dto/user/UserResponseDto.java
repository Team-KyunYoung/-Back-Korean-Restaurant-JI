package com.example.koreanrestaurantji.dto.user;

import com.example.koreanrestaurantji.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String userEmail;
    private final String userNickname;

    public UserResponseDto(User user) {
        this.userEmail = user.getUserEmail();
        this.userNickname = user.getUserNickname();
    }
}