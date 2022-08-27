package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.service.CommentService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"8-2. QNA Comment"})
@RequestMapping(value = "/api/qna/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
}
