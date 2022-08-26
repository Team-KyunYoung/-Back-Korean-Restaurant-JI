package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.Cart.CartRequestDto;
import com.example.koreanrestaurantji.dto.Cart.CartResponseDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.CartService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"6. Cart"})
@RequestMapping(value = "/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장바구니에 음식 추가", notes = "장바구니에 음식 데이터를 추가 합니다.")
    @PostMapping("/create")
    public BaseResponse createCart(@ApiParam(value = "장바구니에 담는 음식 정보를 갖는 객체", required = true) @RequestBody CartRequestDto cartRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), cartService.create(cartRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장바구니 확인", notes = "사용자별 장바구니 데이터를 리턴 합니다.")
    @GetMapping("/find")
    public BaseResponse<List<CartResponseDto>> findCartByUser() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), cartService.findCartByUser());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장바구니 수정", notes = "장바구니 정보를 수정합니다.")
    @PutMapping("/update/{cartNumber}")
    public BaseResponse updateCart(@ApiParam(value = "cartNumber 장바구니 일련번호", required = true) @PathVariable long cartNumber,
                                          @ApiParam(value = "장바구니에 담긴 음식의 변경 수량", required = true) @RequestBody int cartQuantity) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", cartService.update(cartNumber, cartQuantity));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장바구니 음식 삭제", notes = "장바구니에 담긴 음식을 제거합니다.")
    @DeleteMapping("/delete/{cartNumber}")
    public BaseResponse deleteCart(@ApiParam(value = "cartNumber 장바구니 일련번호", required = true) @PathVariable Long cartNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", cartService.delete(cartNumber));
    }
}
