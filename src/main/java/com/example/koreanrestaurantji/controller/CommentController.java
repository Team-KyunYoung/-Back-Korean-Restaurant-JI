package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.comment.CommentRequestDto;
import com.example.koreanrestaurantji.dto.comment.CommentResponseDto;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.CommentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"8-2. QNA Comment"})
@RequestMapping(value = "/api/qna/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "해당 게시글에 Comment 추가", notes = "해당 게시글의 Comment 데이터를 추가 합니다.")
    @PostMapping("/create/{questionNumber}")
    public BaseResponse createComment(@ApiParam(value = "questionNumber 게시글 일련번호", required = true) @PathVariable long questionNumber,
                                      @ApiParam(value = "댓글 데이터", required = true) @RequestBody CommentRequestDto comment) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), commentService.create(questionNumber, comment));
    }

    @ApiOperation(value = "해당 게시글의 댓글 조회", notes = "해당 게시글의 댓글 데이터 전체를 리턴 합니다.")
    @GetMapping("/find/{questionNumber}")
    public BaseResponse<List<CommentResponseDto>> findCommentList(@ApiParam(value = "questionNumber 게시글 일련번호", required = true) @PathVariable long questionNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), commentService.findCommentList(questionNumber));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 수정", notes = "댓글 데이터를 수정합니다.")
    @PutMapping("/update/{commentNumber}")
    public BaseResponse updateComment(@ApiParam(value = "commentNumber 댓글 일련번호", required = true) @PathVariable long commentNumber,
                                        @ApiParam(value = "수정된 게시글 제목과 내용을 담는 객체", required = true) @RequestBody String comment) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", commentService.updateComment(commentNumber, comment));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "댓글 삭제", notes = "댓글 데이터를 제거합니다.")
    @DeleteMapping("/delete/{commentNumber}")
    public BaseResponse deleteComment(@ApiParam(value = "commentNumber 댓글 일련번호", required = true) @PathVariable long commentNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", commentService.delete(commentNumber));
    }
}
