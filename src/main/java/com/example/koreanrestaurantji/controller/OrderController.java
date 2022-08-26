package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.Order.OrderRequestDto;
import com.example.koreanrestaurantji.dto.Order.OrderResponseDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.OrderService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"7. Order"})
@RequestMapping(value = "/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "주문 추가", notes = "주문 데이터를 추가 합니다.")
    @PostMapping("/create")
    public BaseResponse createOrder(@ApiParam(value = "주문 정보를 갖는 객체", required = true) @RequestBody OrderRequestDto orderRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), orderService.create(orderRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "장바구니에서 주문 추가", notes = "주문 데이터를 추가 합니다.")
    @PostMapping("/create/cart")
    public BaseResponse createOrderInCart(@ApiParam(value = "주문 정보를 갖는 객체", required = true) @RequestBody OrderRequestDto orderRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), orderService.createOrderInCart(orderRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "주문 확인", notes = "사용자별 주문 데이터를 리턴 합니다.")
    @GetMapping("/find")
    public BaseResponse<List<OrderResponseDto>> findOrderByUser() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), orderService.findOrderByUser());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "주문 상태 수정", notes = "주문 상태 정보를 수정합니다.")
    @PutMapping("/update/{orderNumber}")
    public BaseResponse updateOrder(@ApiParam(value = "orderNumber 주문 일련번호", required = true) @PathVariable Long orderNumber,
                                          @ApiParam(value = "주문 상태(주문완료/준비중/수령대기/수령완료)", required = true) @RequestBody String orderStatus) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", orderService.update(orderNumber, orderStatus));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "주문 삭제", notes = "주문 정보를 폐기합니다.")
    @DeleteMapping("/delete/{orderNumber}")
    public BaseResponse deleteOrder(@ApiParam(value = "orderNumber 주문 일련번호", required = true) @PathVariable Long orderNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", orderService.delete(orderNumber));
    }
}
