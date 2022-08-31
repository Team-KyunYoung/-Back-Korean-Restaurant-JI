package com.example.koreanrestaurantji.controller;

import com.example.koreanrestaurantji.dto.question.*;
import com.example.koreanrestaurantji.exception.BaseResponse;
import com.example.koreanrestaurantji.exception.BaseResponseCode;
import com.example.koreanrestaurantji.service.QuestionService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {"8-1. Q&A(F&Q)"})
@RequestMapping(value = "/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "QNA 게시글 추가", notes = "QNA 게시글 데이터를 추가 합니다.")
    @PostMapping("/create/qna")
    public BaseResponse createQNA(@ApiParam(value = "게시글 데이터를 갖는 객체", required = true) @RequestBody QNACreateRequestDto QNACreateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), questionService.createQNA(QNACreateRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "FAQ 게시글 추가", notes = "FAQ 게시글 데이터를 추가 합니다.")
    @PostMapping("/create/faq")
    public BaseResponse createFAQ(@ApiParam(value = "게시글 데이터를 갖는 객체", required = true) @RequestBody FAQCreateRequestDto faqCreateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), questionService.createFAQ(faqCreateRequestDto));
    }

    @ApiOperation(value = "QNA 게시글 전체 확인", notes = "모든 게시글 데이터 목록을 리턴 합니다.")
    @GetMapping("/find/public/qna")
    public BaseResponse<List<QNAResponseDto>> findQNAList() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), questionService.findQNAList());
    }

    @ApiOperation(value = "전체 공개인 QNA 게시글 단건 확인", notes = "해당 QNA 게시글 데이터를 리턴 합니다.")
    @GetMapping("/find/public/qna/{questionNumber}")
    public BaseResponse<QuestionPostResponseDto> findQNAPublicPost(@ApiParam(value = "questionNumber 질문 게시글 일련번호", required = true) @PathVariable long questionNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), questionService.findQNAPublicPost(questionNumber));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "비공개인 QNA 게시글 단건 확인", notes = "작성자와 요청자가 같거나 관리자인지 확인 후 해당 QNA 게시글 데이터를 리턴 합니다.")
    @GetMapping("/find/private/qna/{questionNumber}")
    public BaseResponse<QuestionPostResponseDto> findQNAPrivatePost(@ApiParam(value = "questionNumber 질문 게시글 일련번호", required = true) @PathVariable long questionNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), questionService.findQNAPrivatePost(questionNumber));
    }

    @ApiOperation(value = "FAQ 게시글 전체 확인", notes = "모든 게시글 데이터 목록을 리턴 합니다.")
    @GetMapping("/find/public/faq")
    public BaseResponse<List<FAQResponseDto>> findFAQList() throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), questionService.findFAQList());
    }

//    @ApiOperation(value = "FAQ 게시글 단건 확인", notes = "해당 FAQ 게시글 데이터를 리턴 합니다.")
//    @GetMapping("/find/public/faq/{questionNumber}")
//    public BaseResponse<QuestionPostResponseDto> findFAQPost(@ApiParam(value = "questionNumber 질문 게시글 일련번호", required = true) @PathVariable long questionNumber) throws Exception {
//        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), BaseResponseCode.OK.getMessage(), questionService.findFAQPost(questionNumber));
//    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "QNA 게시글 수정", notes = "QNA 게시글 제목/내용/공개여부 데이터를 수정합니다.")
    @PutMapping("/update/qna/{questionNumber}")
    public BaseResponse updateQNAPost(@ApiParam(value = "questionNumber 질문 게시글 일련번호", required = true) @PathVariable long questionNumber,
                                   @ApiParam(value = "수정된 게시글 제목과 내용을 담는 객체", required = true) @RequestBody QNAUpdateRequestDto updateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", questionService.updateQNAPost(questionNumber, updateRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "FAQ 게시글 수정", notes = "FAQ 게시글 제목/내용 데이터를 수정합니다.")
    @PutMapping("/update/faq/{questionNumber}")
    public BaseResponse updateFAQPost(@ApiParam(value = "questionNumber 질문 게시글 일련번호", required = true) @PathVariable long questionNumber,
                               @ApiParam(value = "수정된 게시글 제목과 내용을 담는 객체", required = true) @RequestBody FAQUpdateRequestDto updateRequestDto) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", questionService.updateFAQPost(questionNumber, updateRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "게시글 삭제", notes = "게시글 데이터를 제거합니다.")
    @DeleteMapping("/delete/{questionNumber}")
    public BaseResponse delete(@ApiParam(value = "questionNumber 질문 게시글 일련번호", required = true) @PathVariable Long questionNumber) throws Exception {
        return new BaseResponse(BaseResponseCode.OK.getHttpStatus(), "요청 성공했습니다.", questionService.delete(questionNumber));
    }

}
