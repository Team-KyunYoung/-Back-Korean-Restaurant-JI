package com.example.koreanrestaurantji.controller;


import com.example.koreanrestaurantji.dto.user.UserLoginRequestDto;
import com.example.koreanrestaurantji.dto.user.UserLoginResponseDto;
import com.example.koreanrestaurantji.dto.user.UserSignupRequestDto;
import com.example.koreanrestaurantji.dto.user.UserDeleteRequestDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController //(@Controller + @ResponseBody)  Json 형태로 객체 데이터를 반환하는 컨트롤러
@Api(tags = {"User"})  //Swagger 리소스(대표 제목) 명시
@RequestMapping(value = "/api/user")  //특정 url을 요청을 수행하도록 mapping (이 클래스의 모든 mapper는 /user로 시작한다)
@RequiredArgsConstructor //필드의 생성자를 자동 생성해주는 롬복 어노테이션(Autowired 안써도됨)
public class UserController {

    private final UserService userService; //실제 데이터 처리를 하는 Service 파일

    @ApiOperation(value = "회원가입", notes = "회원가입을 합니다.") //@ApiOperation : API에 대한 간략한 설명
    @PostMapping("/signup")     //@ApiParam : 파라미터 정보 명시
    public BaseResponse<Long> signup(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserSignupRequestDto userSignupRequestDto) throws Exception {
        //exception/BaseResponse 에 정의된 데이터가 리턴된다. >> 상태코드, 에러나 상황 설명, 실제 처리된 데이타 리스트가 보내짐.
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.signUp(userSignupRequestDto));
    }

    @ApiOperation(value = "로그인", notes = "이메일로 로그인을 합니다.")
    @PostMapping("/login")
    //PostMapping : (RestAPI) 파라미터에 비밀번호가 포함되므로 PostMapping 사용             // @RequestBody : json기반의 파라미터에 사용. HTTP 요청의 바디내용을 통째로 자바객체로 변환해서 매핑된 메소드 파라미터로 전달됨.
    public BaseResponse<UserLoginResponseDto> login(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserLoginRequestDto userLoginDto) throws Exception {
        // ^ : BaseResponse 형식으로 리턴되므로 함수 형식이 BaseResponse이며, BaseResponse<T>의 T는 data의 형식을 정의한다.
        // 이때, T의 형식은 userService.login()의 리턴 데이터의 형식과 같아야 한다.
        return new BaseResponse(userService.login(userLoginDto).getStatus(), "요청 성공했습니다.", userService.login(userLoginDto));
    }

    @ApiOperation(value = "닉네임 체크", notes = "닉네임 중복 여부 체크")
    @PostMapping("/checknickname")
    public BaseResponse<UserLoginResponseDto> nicknameCheck(@ApiParam(value = "회원가입 닉네임", required = true) @RequestBody String nickname) throws Exception {
        return new BaseResponse(userService.nicknameCheck(nickname).getStatus(), "요청 성공했습니다.", userService.nicknameCheck(nickname));
    }

    @ApiOperation(value = "회원가입 이메일 인증", notes = "이메일 중복 여부 체크와 이메일 인증번호 전송")
    @PostMapping("/signup/emailAuth")
    public BaseResponse<String> signupEmailAuth(@ApiParam(value = "회원가입 이메일", required = true) @RequestBody String userEmail) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.signupEmailAuth(userEmail));
    }
    @ApiOperation(value = "비밀번호 찾기 이메일 인증", notes = "이메일 체크와 이메일 인증번호 전송")
    @PostMapping("/update/emailAuth")
    public BaseResponse<String> updateEmailAuth(@ApiParam(value = "비밀번호 찾기 이메일", required = true) @RequestBody String userEmail) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), userService.updateEmailAuth(userEmail));
    }
    //사용자 삭제
    @ApiOperation(value = "회원 삭제", notes = "회원 정보를 폐기합니다.")
    @DeleteMapping("/delete")
    //PostMapping : (RestAPI) 파라미터에 비밀번호가 포함되므로 PostMapping 사용             // @RequestBody : json기반의 파라미터에 사용. HTTP 요청의 바디내용을 통째로 자바객체로 변환해서 매핑된 메소드 파라미터로 전달됨.
    public BaseResponse<UserLoginResponseDto> deleteUser(@ApiParam(value = "회원 한 명의 정보를 갖는 객체", required = true) @RequestBody UserDeleteRequestDto userDeleteRequestDto) throws Exception {
        return new BaseResponse(userService.deleteUser(userDeleteRequestDto).getStatus(), "요청 성공했습니다.", userService.deleteUser(userDeleteRequestDto));
    }
}
