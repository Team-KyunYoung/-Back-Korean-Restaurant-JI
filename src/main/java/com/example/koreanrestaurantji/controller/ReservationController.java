package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.reservation.ReservationRequestDto;
import com.example.koreanrestaurantji.dto.reservation.ReservationResponseDto;
import com.example.koreanrestaurantji.dto.reservation.ReservationUpdateRequestDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.ReservationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"5. Reservation"})
@RequestMapping(value = "/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "예약 추가", notes = "예약 데이터를 추가 합니다.")
    @PostMapping("/create")
    public BaseResponse createReservation(@ApiParam(value = "예약 정보를 갖는 객체", required = true) @RequestBody ReservationRequestDto reservationRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reservationService.create(reservationRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "지난 예약 확인", notes = "사용자별 지난 예약 데이터를 리턴 합니다.")
    @GetMapping("/find/before")
    public BaseResponse<List<ReservationResponseDto>> findByUserBeforeDate() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reservationService.findByUserBeforeDate());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "오늘 이후 예약 확인", notes = "사용자별 예약 데이터를 리턴 합니다.")
    @GetMapping("/find/after")
    public BaseResponse<List<ReservationResponseDto>> findByUserAfterDate() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reservationService.findByUserAfterDate());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "예약 수정", notes = "예약 정보를 수정합니다.")
    @PutMapping("/update/{reservationNumber}")
    public BaseResponse updateReservation(@ApiParam(value = "reservationNumber 예약 일련번호", required = true) @PathVariable Long reservationNumber,
                                          @ApiParam(value = "수정된 예약 정보를 담는 객체", required = true) @RequestBody ReservationUpdateRequestDto reservationUpdateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", reservationService.update(reservationNumber, reservationUpdateRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "예약 삭제", notes = "예약 정보를 폐기합니다.")
    @DeleteMapping("/delete/{reservationNumber}")
    public BaseResponse deleteReservation(@ApiParam(value = "reservationNumber 예약 일련번호", required = true) @PathVariable Long reservationNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", reservationService.delete(reservationNumber));
    }
}
