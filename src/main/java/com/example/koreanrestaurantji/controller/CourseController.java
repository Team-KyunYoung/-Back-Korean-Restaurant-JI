package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.course.CourseCreateRequestDto;
import com.example.koreanrestaurantji.dto.course.CourseResponseDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"3. Course"})
@RequestMapping(value = "/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @ApiOperation(value = "코스 추가", notes = "코스 데이터를 추가 합니다.")
    @PostMapping("/create")
    public BaseResponse createCourse(@ApiParam(value = "코스별 음식 이름을 담는 객체", required = true) @RequestBody CourseCreateRequestDto courseCreateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), courseService.create(courseCreateRequestDto));
    }

    @ApiOperation(value = "코스 정보 전체 조회", notes = "코스 정보 전체 조회")
    @GetMapping("/find")
    public BaseResponse<List<CourseResponseDto>> allCourse() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), courseService.allCourse());
    }

    @ApiOperation(value = "코스 정보 조회", notes = "코스 정보 단건 조회")
    @GetMapping("/find/{number}")
    public BaseResponse<CourseResponseDto> findCourse(@ApiParam(value = "CourseNumber 코스 일련번호", required = true) @PathVariable Long number) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), courseService.findCourse(number));
    }

    @ApiOperation(value = "코스 삭제", notes = "코스 정보를 폐기합니다.")
    @DeleteMapping("/delete/{number}")
    public BaseResponse deleteCourse(@ApiParam(value = "CourseNumber 코스 일련번호", required = true) @PathVariable Long number) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", courseService.delete(number));
    }
}
