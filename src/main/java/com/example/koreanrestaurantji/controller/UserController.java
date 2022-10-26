package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.SuccessResponseDto;
import com.example.koreanrestaurantji.dto.user.*;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"1. User"})
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.")
    @PostMapping("/signup")
    public BaseResponse<Long> signup(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserSignupRequestDto userSignupRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.signUp(userSignupRequestDto));
    }

    @ApiOperation(value = "닉네임 체크", notes = "닉네임 중복 여부 체크")
    @PostMapping("/checknickname")
    public BaseResponse<SuccessResponseDto> nicknameCheck(@ApiParam(value = "회원가입 닉네임", required = true) @RequestBody UserCheckNameRequestDto userCheckNameRequestDto) throws Exception {
        return new BaseResponse(userService.nicknameCheck(userCheckNameRequestDto.getUserNickname()).getStatus(), "요청 성공했습니다.", userService.nicknameCheck(userCheckNameRequestDto.getUserNickname()));
    }

    @ApiOperation(value = "회원가입 이메일 인증", notes = "이메일 중복 여부 체크와 이메일 인증번호 전송")
    @PostMapping("/signup/emailAuth")
    public BaseResponse<String> signupEmailAuth(@ApiParam(value = "회원가입 이메일", required = true) @RequestBody UserEmailAuthRequestDto userEmailAuthRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.signupEmailAuth(userEmailAuthRequestDto.getUserEmail()));
    }

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    public BaseResponse<UserLoginResponseDto> login(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserLoginRequestDto userLoginDto) throws Exception {
        return new BaseResponse(userService.login(userLoginDto).getStatus(), "요청 성공했습니다.", userService.login(userLoginDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "전체 사용자 정보 조회", notes = "사용자 정보 전체 조회")
    @GetMapping("/find/all")
    public BaseResponse<List<UserAdminResponseDto>> findAll() {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "사용자 정보 조회", notes = "사용자 정보 단건 조회")
    @GetMapping("/find")
    public BaseResponse<UserResponseDto> findByUser() {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.findByUser());
    }

    @ApiOperation(value = "비밀번호 찾기 이메일 인증", notes = "이메일 체크와 이메일 인증번호 전송")
    @PostMapping("/find/emailAuth")
    public BaseResponse<String> updateEmailAuth(@ApiParam(value = "비밀번호 찾기 이메일", required = true) @RequestBody UserEmailAuthRequestDto userEmailAuthRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.findEmailAuth(userEmailAuthRequestDto.getUserEmail()));
    }

    @ApiOperation(value = "비밀번호 변경", notes = "사용자 비밀번호를 변경합니다.")
    @PutMapping("/find/update/password")
    public BaseResponse<SuccessResponseDto> findUpdatePassword(@ApiParam(value = "변경할 패스워드", required = true) @RequestBody UserFindUpdatePwdRequestDto userFindUpdatePwdRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.findUpdatePassword(userFindUpdatePwdRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "닉네임 변경", notes = "사용자 닉네임을 변경합니다.")
    @PutMapping("/update/nickname")
    public BaseResponse<SuccessResponseDto> updateNickname(@ApiParam(value = "변경할 닉네임", required = true) @RequestBody UserUpdateNameRequestDto userUpdateNameDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.updateNickname(userUpdateNameDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "비밀번호 확인", notes = "사용자 비밀번호가 맞는지 확인합니다.")
    @PostMapping("/verify/password")
    public BaseResponse<SuccessResponseDto> verifyPassword(@ApiParam(value = "확인 할 패스워드", required = true) @RequestBody UserUpdatePwdRequestDto userUpdatePwdDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.verifyPassword(userUpdatePwdDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "비밀번호 변경", notes = "사용자 비밀번호를 변경합니다.")
    @PutMapping("/update/password")
    public BaseResponse<SuccessResponseDto> updatePassword(@ApiParam(value = "변경할 패스워드", required = true) @RequestBody UserUpdatePwdRequestDto userUpdatePwdDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.updatePassword(userUpdatePwdDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "회원 정보를 폐기합니다.")
    @DeleteMapping("/delete")
    public BaseResponse<SuccessResponseDto> deleteUser() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", userService.deleteUser());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "관리자 페이지 회원 삭제", notes = "관리자가 회원 정보를 폐기합니다.")
    @DeleteMapping("/delete/{userNumber}")
    public BaseResponse<SuccessResponseDto> deleteUserByNumber(@ApiParam(value = "userNumber 사용자 일련번호", required = true) @PathVariable Long userNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", userService.deleteUserByNumber(userNumber));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "로그인한 유저가 관리자인지 확인", notes = "로그인한 유저가 관리자인지 여부를 반환합니다.")
    @GetMapping("/isadmin")
    public BaseResponse<SuccessResponseDto> userAdminCheck() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", userService.userAdminCheck());
    }
}
