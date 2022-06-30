package com.example.koreanrestaurantji.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Join;

@RestController
//@RequestMapping(value = "/member/v1")
@Api(tags = {"Test API"})
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/hello-world")
    @ApiOperation(value = "테스트", response = Join.class)
    public  String helloWorld() {
        return "hello-world";
    }

}
