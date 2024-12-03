package spring.hi_hello_spring.quiz.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.quiz.command.application.dto.QuizCreateDTO;
import spring.hi_hello_spring.quiz.command.application.dto.QuizUpdateDTO;
import spring.hi_hello_spring.quiz.command.application.service.QuizService;

@RestController
@RequestMapping("api/v1/hr/quizCategory")
@RequiredArgsConstructor
@Tag(name = "Quiz API", description = "퀴즈 관련 API")
public class QuizController {

    private final QuizService quizService;

    /* 카테고리 별 퀴즈 등록 */
    @PostMapping("/{quizCategorySeq}/quiz")
    @Operation(summary = "카테고리 별 퀴즈 생성", description = "카테고리별 퀴즈 생성 로직입니다.")
    public ApiResponse<?> createQuiz(@PathVariable Long quizCategorySeq, QuizCreateDTO quizCreateDTO){

        quizService.createQuiz(quizCategorySeq, quizCreateDTO);
        return ResponseUtil.successResponse("퀴즈가 성공적으로 추가 되었습니다.").getBody();
    }

    /* 카테고리 별 퀴즈 수정 */
    @PutMapping("/{quizCategorySeq}/quiz/{quizSeq}")
    @Operation(summary = "카테고리 별 퀴즈 수정", description = "카테고리별 퀴즈 수정 로직입니다.")
    public ApiResponse<?> updateQuiz(@PathVariable Long quizCategorySeq,
                                     @PathVariable Long quizSeq,
                                     QuizUpdateDTO quizUpdateDTO){

        quizService.updateQuiz( quizSeq, quizCategorySeq, quizUpdateDTO);
        return ResponseUtil.successResponse("퀴즈가 성공적으로 수정 되었습니다.").getBody();
    }

    /* 카테고리 별 퀴즈 삭제 */
    @DeleteMapping("/{quizCategorySeq}/quiz/{quizSeq}")
    @Operation(summary = "카테고리 별 퀴즈 삭제", description = "카테고리별 퀴즈 삭제 로직입니다.")
    public ApiResponse<?> deleteQuiz(@PathVariable Long quizCategorySeq, @PathVariable Long quizSeq){

        quizService.deleteQuiz(quizSeq, quizCategorySeq);
        return ResponseUtil.successResponse("퀴즈가 성공적으로 삭제 되었습니다.").getBody();
    }
}
