package com.example.koreanrestaurantji.dto.user;

import com.example.koreanrestaurantji.domain.User;
import lombok.Getter;

@Getter
public class UserUpdateNameRequestDto {

    private Long userId;
    private String userNickname;

    public UserUpdateNameRequestDto(Long userId, String userNickname) {
        this.userId = userId;
        this.userNickname = userNickname;
    }
}
