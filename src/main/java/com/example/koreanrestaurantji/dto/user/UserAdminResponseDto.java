package com.example.koreanrestaurantji.dto.user;

import com.example.koreanrestaurantji.domain.User;
import lombok.Getter;

@Getter
public class UserAdminResponseDto {
    private final Long userNumber;
    private final boolean role;
    private final String userEmail;
    private final String userNickname;

    public UserAdminResponseDto(User user) {
        this.userNumber = user.getUserNumber();
        this.role = user.isRole();
        this.userEmail = user.getUserEmail();
        this.userNickname = user.getUserNickname();
    }
}
