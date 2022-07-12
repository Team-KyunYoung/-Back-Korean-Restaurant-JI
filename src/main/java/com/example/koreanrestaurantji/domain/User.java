package com.example.koreanrestaurantji.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@ApiModel(value = "회원 정보", description = "아이디, 이메일, 비밀번호 등 회원 정보를 가진 Class")
@Entity(name = "user")
@Getter
@NoArgsConstructor
@Table(name = "USER")
public class User {

    @ApiModelProperty(value = "아이디")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "이메일")
    @Column(name = "user_email", nullable = false, unique = true,  length = 40)
    private String userEmail;

    @ApiModelProperty(value = "비밀번호")
    @Column(name = "user_password", nullable = false,  length = 20)
    private String userPassword;

    @ApiModelProperty(value = "별명")
    @Column(name = "user_nickname", nullable = false,  length = 20)
    private String userNickname;

    @ApiModelProperty(value = "역할")
    @Column(name = "role", nullable = false)
    @ColumnDefault("false")
    private boolean role;

    @Builder
    public User(Long userId, String userEmail, String userPassword, String userNickname, boolean role) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.role = role;
    }

    public String getPassword() {
        return this.userPassword;
    }
}
