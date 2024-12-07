package spring.hi_hello_spring.group.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.group.query.dto.TaskGroupListQueryDTO;
import spring.hi_hello_spring.group.query.service.TaskGroupQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr")
@RequiredArgsConstructor
@Tag(name = "TaskGroup API", description = "그룹 과제 관련 API")
public class TaskGroupQueryController {

    private final TaskGroupQueryService taskGroupQueryService;

    /* 그룹 과제 별 그룹 리스트 조회 */
    @GetMapping("/group/task/{taskSeq}/group/list")
    @Operation(summary = "동료 평가 지표 조회", description = "동료 평가 지표 조회 로직입니다.")
    public ApiResponse<?> getTaskGroupList(@PathVariable Long taskSeq) {

        List<TaskGroupListQueryDTO> queryDTO = taskGroupQueryService.getTaskGroupList(taskSeq);
        return ResponseUtil.successResponse("그룹 과제 별 그룹 리스트가 성공적으로 조회되었습니다.", queryDTO).getBody();
    }
}
