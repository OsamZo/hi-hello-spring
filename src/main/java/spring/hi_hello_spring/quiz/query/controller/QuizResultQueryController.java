package spring.hi_hello_spring.quiz.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.quiz.query.dto.QuizResultAllQueryDTO;
import spring.hi_hello_spring.quiz.query.service.QuizResultQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/quizCategory/{quizCategorySeq}/quiz/result")
@RequiredArgsConstructor
@Tag(name = "Quiz Result API", description = "퀴즈 결과 관련 API")
public class QuizResultQueryController {

    private final QuizResultQueryService quizResultQueryService;

    /* 퀴즈 카테고리 별 퀴즈 결과 목록 전체 조회 */
    @GetMapping
    @Operation(summary = "카테고리 별 퀴즈 결과 조회", description = "카테고리별 퀴즈 결과 조회 로직입니다.")
    public ApiResponse<?> getAllQuizResult(@PathVariable Long quizCategorySeq){

        List<QuizResultAllQueryDTO> queryDTO = quizResultQueryService.getAllQuizResult(quizCategorySeq);
        return ResponseUtil.successResponse("카테고리 별 퀴즈 결과가 성공적으로 조회되었습니다.", queryDTO).getBody();
    }
}
