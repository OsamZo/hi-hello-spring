package spring.hi_hello_spring.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.evaluation.query.dto.TaskHrAllListQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskMentorDetailQueryDTO;
import spring.hi_hello_spring.evaluation.query.dto.TaskAllListQueryDTO;
import spring.hi_hello_spring.evaluation.query.service.TaskQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "과제 관련 API")
public class TaskQueryController {

    private final TaskQueryService taskQueryService;

    @GetMapping("/hr/task")
    @Operation(summary = "담당자 과제 리스트 조회", description = "담당자가 조회하는 과제 리스트 조회 기능입니다.")
    public ApiResponse<?> getAllTaskList() {

        List<TaskAllListQueryDTO> taskAllListQueryDTO = taskQueryService.getHrAllTaskList();
        return ResponseUtil.successResponse("멘티 전체 조회가 성공적으로 조회되었습니다.", taskAllListQueryDTO).getBody();
    }

    @GetMapping("/mentor/task")
    @Operation(summary = "멘토 과제 리스트 조회", description = "멘토가 조회하는 과제 리스트 조회 기능입니다.")
    public ApiResponse<?> getAllMentorTaskList() {

        List<TaskAllListQueryDTO> taskAllListQueryDTO = taskQueryService.getMentorAllTaskList();
        return ResponseUtil.successResponse("멘티 전체 조회가 성공적으로 조회되었습니다.", taskAllListQueryDTO).getBody();
    }

    @GetMapping("/mentor/task/{taskSeq}")
    @Operation(summary = "멘토 과제 상세 조회", description = "멘토가 과제를 상세조회하는 기능입니다.")
    public ApiResponse<?> getMentorTaskDetail(@PathVariable Long taskSeq) {

        List<TaskMentorDetailQueryDTO> taskMentorDetailQueryDTO = taskQueryService.getMentorTaskDetail(taskSeq);
        return ResponseUtil.successResponse("멘티 전체 조회가 성공적으로 조회되었습니다.", taskMentorDetailQueryDTO).getBody();
    }
}
