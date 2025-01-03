package spring.hi_hello_spring.onboarding.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.onboarding.command.application.dto.CheckListUpdateDTO;
import spring.hi_hello_spring.onboarding.command.application.service.CheckListService;

@RestController
@RequestMapping("api/v1/onboarding")
@RequiredArgsConstructor
@Tag(name = "Template API", description = "온보딩 스토리 보드 관련 API")
public class CheckListController {
    private final CheckListService checkListService;

    @PutMapping("/checklist")
    @Operation(summary = "체크리스트 완료 업데이트", description = "체크리스트 완료 업데이트 로직입니다.")
    public ApiResponse<?> createTemplate(@RequestBody CheckListUpdateDTO checklistUpdateDTO){

        checkListService.checkListUpdate(checklistUpdateDTO);
        return ResponseUtil.successResponse("체크리스트 수행여부가 성공적으로 수정되었습니다.").getBody();
    }
}
