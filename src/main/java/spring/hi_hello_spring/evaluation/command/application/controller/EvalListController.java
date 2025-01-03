package spring.hi_hello_spring.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.command.application.dto.EvalListCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.service.EvalListService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "EvalList API", description = "과제 평가 항목 관련 API")
public class EvalListController {

    private final EvalListService evalListService;

    @PostMapping(value= "/eval-ind/{evalIndSeq}/task/{taskSeq}/eval-list")
    @Operation(summary = "과제 평가 항목 등록", description = "과제 평가 항목 등록 로직입니다.")
    public ApiResponse<?> createEvalList(
            @PathVariable("evalIndSeq") Long evalIndSeq,
            @PathVariable("taskSeq") Long taskSeq,
            @RequestBody EvalListCreateDTO evalListCreateDTO
            ) {
        evalListService.createEvalList(evalIndSeq, taskSeq, evalListCreateDTO);
        return ResponseUtil.successResponse("과제 평가 항목이 성공적으로 등록되었습니다.").getBody();
    }

    @DeleteMapping(value="/eval-list/{evalListSeq}")
    @Operation(summary= "과제 평가 항목 삭제", description = "과제 평가 항목 삭제 로직입니다.")
    public ApiResponse<?> deleteEvalList(
            @PathVariable("evalListSeq") Long evalListSeq
    ){
        evalListService.deleteEvalList(evalListSeq);
        return ResponseUtil.successResponse("과제 평가 항목이 성공적으로 삭제되었습니다.").getBody();
    }
}