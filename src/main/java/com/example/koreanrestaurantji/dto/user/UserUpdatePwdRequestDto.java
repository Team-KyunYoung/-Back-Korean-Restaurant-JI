package com.example.koreanrestaurantji.dto.user;

import com.example.koreanrestaurantji.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePwdRequestDto {
    private String userPassword;
}
