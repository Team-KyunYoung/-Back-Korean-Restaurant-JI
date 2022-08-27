package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.event.EventCreateRequestDto;
import com.example.koreanrestaurantji.dto.event.EventPostResponseDto;
import com.example.koreanrestaurantji.dto.event.EventResponseDto;
import com.example.koreanrestaurantji.dto.event.EventUpdateRequestDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.EventService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"9. Event"})
@RequestMapping(value = "/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Event 게시글 추가", notes = "Event 게시글 데이터를 추가 합니다.")
    @PostMapping("/create/event")
    public BaseResponse createEvent(@ApiParam(value = "게시글 데이터를 갖는 객체", required = true) @RequestBody EventCreateRequestDto eventCreateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), eventService.create(eventCreateRequestDto));
    }

    @ApiOperation(value = "Event 게시글 전체 확인", notes = "모든 Event 게시글 데이터 목록을 리턴 합니다.")
    @GetMapping("/find/event")
    public BaseResponse<List<EventResponseDto>> findEventList() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), eventService.findEventList());
    }

    @ApiOperation(value = "Event 게시글 단건 확인", notes = "해당 Event 게시글 데이터를 리턴 합니다.")
    @GetMapping("/find/{eventNumber}")
    public BaseResponse<EventPostResponseDto> findEventPost(@ApiParam(value = "eventNumber 이벤트 게시글 일련번호", required = true) @PathVariable long eventNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), eventService.findEventPost(eventNumber));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Event 게시글 수정", notes = "Event 게시글 제목/내용 데이터를 수정합니다.")
    @PutMapping("/update/qna/{eventNumber}")
    public BaseResponse updateEventPost(@ApiParam(value = "questionNumber Event 게시글 일련번호", required = true) @PathVariable long eventNumber,
                                      @ApiParam(value = "수정된 게시글 제목과 내용을 담는 객체", required = true) @RequestBody EventUpdateRequestDto eventUpdateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", eventService.updateEventPost(eventNumber, eventUpdateRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시글 삭제", notes = "게시글 데이터를 제거합니다.")
    @DeleteMapping("/delete/{eventNumber}")
    public BaseResponse deleteEvent(@ApiParam(value = "eventNumber Event 게시글 일련번호", required = true) @PathVariable long eventNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", eventService.delete(eventNumber));
    }
}
