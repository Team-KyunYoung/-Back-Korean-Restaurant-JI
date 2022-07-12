package com.example.koreanrestaurantji.controller;


import com.example.koreanrestaurantji.dto.user.UserLoginRequestDto;
import com.example.koreanrestaurantji.dto.user.UserLoginResponseDto;
import com.example.koreanrestaurantji.dto.user.UserSignupRequestDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"User"})
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public BaseResponse<Long> signup(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserSignupRequestDto userSignupRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.signUp(userSignupRequestDto));
    }

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public BaseResponse<UserLoginResponseDto> login(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserLoginRequestDto userLoginDto) throws Exception {
        return new BaseResponse(userService.login(userLoginDto).getStatus(), "요청 성공했습니다.", userService.login(userLoginDto));
    }

}