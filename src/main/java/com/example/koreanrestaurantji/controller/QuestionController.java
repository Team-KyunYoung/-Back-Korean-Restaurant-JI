package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.service.QuestionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"8-1. Q&A(F&Q)"})
@RequestMapping(value = "/api/qna")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

}
