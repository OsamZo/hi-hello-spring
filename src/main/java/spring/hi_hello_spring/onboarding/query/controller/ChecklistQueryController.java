package spring.hi_hello_spring.onboarding.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.employee.query.service.EmployeeQueryService;
import spring.hi_hello_spring.onboarding.query.service.ChecklistQueryService;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name="Checklist API", description = "체크리스트 관련 API")
public class ChecklistQueryController {

    private final ChecklistQueryService checklistQueryService;
    private final EmployeeQueryService employeeQueryService;

    @GetMapping("/template/{templateSeq}/checklist/status")
    @Operation(summary = "체크리스트 수행 완료 상태 조회", description = "체크리스트 수행 완료 상태 조회 로직입니다.")
    public ApiResponse<?> getChecklistStatus(@PathVariable("templateSeq") Long templateSeq, @RequestParam String userRole) {
        Long menteeSeq;

        if (Objects.equals(userRole, "MENTEE")) {
            menteeSeq = CustomUserUtils.getCurrentEmployeeSeq();
        } else if (Objects.equals(userRole, "MENTOR")) {
            menteeSeq = employeeQueryService.getMenteeInfo().getEmployeeSeq();
        } else {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
        boolean checklistStatus = checklistQueryService.getChecklistStatusByTemplateSeq(templateSeq, menteeSeq);
        return ResponseUtil.successResponse("체크리스트 수행 완료 상태가 성공적으로 조회되었습니다.", checklistStatus).getBody();
    }
}