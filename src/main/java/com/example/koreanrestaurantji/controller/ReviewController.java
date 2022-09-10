package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.review.ReviewRequestDto;
import com.example.koreanrestaurantji.dto.review.ReviewResponseDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"10. Review"})
@RequestMapping(value = "/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Review 추가", notes = "Review 데이터를 추가 합니다.")
    @PostMapping("/create")
    public BaseResponse createReview(@ApiParam(value = "Review 데이터를 갖는 객체", required = true) @RequestBody ReviewRequestDto reviewRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.create(reviewRequestDto));
    }

    @ApiOperation(value = "Review 전체 확인", notes = "모든 Review 데이터 목록을 리턴 합니다.")
    @GetMapping("/find")
    public BaseResponse<List<ReviewResponseDto>> findAll() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), reviewService.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Review 수정", notes = "Review 이미지/내용 데이터를 수정합니다.")
    @PutMapping("/update/{reviewNumber}")
    public BaseResponse updateReview(@ApiParam(value = "reviewNumber Review 일련번호", required = true) @PathVariable long reviewNumber,
                                        @ApiParam(value = "수정된 이미지, 내용을 담는 객체", required = true) @RequestBody ReviewRequestDto reviewRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", reviewService.updateReview(reviewNumber, reviewRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "Review 삭제", notes = "Review 데이터를 제거합니다.")
    @DeleteMapping("/delete/{reviewNumber}")
    public BaseResponse deleteReview(@ApiParam(value = "reviewNumber Review 일련번호", required = true) @PathVariable long reviewNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", reviewService.delete(reviewNumber));
    }
}
