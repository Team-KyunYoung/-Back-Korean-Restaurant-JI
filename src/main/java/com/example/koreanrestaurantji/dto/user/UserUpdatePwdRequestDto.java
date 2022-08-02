package com.example.koreanrestaurantji.dto.user;

import com.example.koreanrestaurantji.domain.User;
import lombok.Getter;

@Getter
public class UserUpdatePwdRequestDto {
    private Long userId;
    private String userPassword;

    public UserUpdatePwdRequestDto(Long userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }
}
