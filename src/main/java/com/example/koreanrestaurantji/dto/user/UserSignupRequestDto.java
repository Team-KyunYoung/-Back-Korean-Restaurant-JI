package com.example.koreanrestaurantji.dto.user;

import com.example.koreanrestaurantji.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupRequestDto {
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userNickname;

    @Builder
    public UserSignupRequestDto(String userEmail, String userPassword, String userNickname) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
    }

    public User toEntity() {
        return User.builder()
                .userEmail(userEmail)
                .userPassword(userPassword)
                .userNickname(userNickname)
                .build();
    }
}
