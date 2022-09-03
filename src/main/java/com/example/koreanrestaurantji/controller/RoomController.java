package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.room.*;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.RoomService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {"4. Room"})
@RequestMapping(value = "/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "객실 추가", notes = "객실 데이터를 추가 합니다.")
    @PostMapping("/create")
    public BaseResponse createRoom(@ApiParam(value = "객실 이름,이미지를 담는 객체", required = true) @RequestBody RoomRequestDto roomRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), roomService.create(roomRequestDto));
    }

    @ApiOperation(value = "객실 조희", notes = "존재하는 객실의 이름을 리턴합니다.")
    @GetMapping("/find/room")
    public BaseResponse<RoomDataResponseDto> roomAllStatus() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), roomService.roomAllName());
    }

    @ApiOperation(value = "객실별 예약 현황 전체 조회", notes = "객실 별 모든 날짜, 시간별 예약 현황을 리턴합니다.")
    @GetMapping("/find/{roomNumber}")
    public BaseResponse<RoomResponseDto> roomAllStatus(@ApiParam(value = "roomNumber 객실 일련번호", required = true) @PathVariable Long roomNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), roomService.roomAllStatus(roomNumber));
    }

    @ApiOperation(value = "특정 객실-날짜의 예약 현황 전체 조회", notes = "특정 객실-날짜의 예약 현황을 리턴합니다.")
    @PostMapping("/find/{roomNumber}/date")
    public BaseResponse<RoomResponseDto> roomStatus(@ApiParam(value = "roomNumber 객실 일련번호", required = true) @PathVariable Long roomNumber,
                                                    @ApiParam(value = "예약 날짜", required = true) @RequestBody RoomStatusDateRequestDto roomStatusDateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), roomService.roomStatus(roomNumber, roomStatusDateRequestDto));
    }

    @ApiOperation(value = "특정 객실-날짜-시간의 남은 좌석 수 조회", notes = "특정 객실-날짜-시간의 남은 좌석 수를 리턴합니다.")
    @PostMapping("/find/{roomNumber}/date/time")
    public BaseResponse<String> roomRemaining(@ApiParam(value = "roomNumber 객실 일련번호", required = true) @PathVariable Long roomNumber,
                                               @ApiParam(value = "예약 날짜", required = true) @RequestBody RoomStatusTimeRequestDto roomStatusTimeRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), roomService.roomRemaining(roomNumber, roomStatusTimeRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "객실 삭제", notes = "객실 정보를 폐기합니다.")
    @DeleteMapping("/delete/{roomNumber}")
    public BaseResponse deleteCourse(@ApiParam(value = "roomNumber 객실 일련번호", required = true) @PathVariable Long roomNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", roomService.delete(roomNumber));
    }
}
