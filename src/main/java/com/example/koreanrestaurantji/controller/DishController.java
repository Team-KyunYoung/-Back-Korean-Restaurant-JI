package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.dish.DishCreateRequestDto;
import com.example.koreanrestaurantji.dto.dish.DishResponseDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {"2. Dish"})
@RequestMapping(value = "/api/dish")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @ApiOperation(value = "요리 추가", notes = "음식 데이터를 추가 합니다.")
    @PostMapping("/create")
    public BaseResponse dishCreate(@ApiParam(value = "음식 정보를 갖는 객체", required = true) @RequestBody DishCreateRequestDto dishCreateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), dishService.create(dishCreateRequestDto));
    }

    @ApiOperation(value = "음식 정보 조회", notes = "음식 정보 단건 조회")
    @GetMapping("/find/{number}")
    public BaseResponse<DishResponseDto> findDish(@ApiParam(value = "DishNumber 음식 일련번호", required = true) @PathVariable Long number) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), dishService.findDish(number));
    }

    @ApiOperation(value = "음식 삭제", notes = "음식 정보를 폐기합니다.")
    @DeleteMapping("/delete/{number}")
    public BaseResponse deleteDish(@ApiParam(value = "DishNumber 음식 일련번호", required = true) @PathVariable Long number) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", dishService.delete(number));
    }
}
