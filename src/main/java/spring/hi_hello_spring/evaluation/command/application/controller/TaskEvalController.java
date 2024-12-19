package spring.hi_hello_spring.evaluation.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.command.application.dto.EvalGroupCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.dto.TaskEvalCreateDTO;
import spring.hi_hello_spring.evaluation.command.application.service.TaskEvalService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@Tag(name = "TaskEval API", description = "과제 평가 관련 API")
public class TaskEvalController {

    private final TaskEvalService taskEvalService;

    @PostMapping("/task-submit/{taskSubmitSeq}/task-eval")
    @Operation(summary = "과제 평가 등록", description = "과제 평가 등록 로직입니다.")
    public ApiResponse<?> createTaskEval(
            @PathVariable("taskSubmitSeq") Long taskSubmitSeq,
            @RequestBody List<TaskEvalCreateDTO> taskEvalCreateDTOS
    )
    {
        taskEvalService.createTaskEval(taskSubmitSeq, taskEvalCreateDTOS);
        return ResponseUtil.successResponse("과제 평가가 성공적으로 등록되었습니다.").getBody();
    }

    @PostMapping("mentee/task/group/{taskGroupSeq}")
    @Operation(summary = "그룹과제 과제 평가 등록", description = "그룹과제 평가를 등록합니다.")
    public ApiResponse<?> createTaskGroupEval(@RequestBody List<EvalGroupCreateDTO> evalGroupCreateDTO){
        taskEvalService.createTaskGroupEval(evalGroupCreateDTO);
        return ResponseUtil.successResponse("그룹과제 평가가 성공적으로 등록되었습니다.").getBody();
    }
}