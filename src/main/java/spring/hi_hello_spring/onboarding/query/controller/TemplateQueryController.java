package spring.hi_hello_spring.onboarding.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.onboarding.query.dto.TemplateAllQueryDTO;
import spring.hi_hello_spring.onboarding.query.service.TemplateQueryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/onboarding")
@RequiredArgsConstructor
@Tag(name = "Template API", description = "온보딩 스토리 보드 관련 API")
public class TemplateQueryController {

    private final TemplateQueryService templateQueryService;

    /* 온보딩 스토리보드 순서 전체 조회 */
    @GetMapping
    @Operation(summary = "온보딩 스토리 보드 순서 전체 조회", description = "온보딩 스토리 보드 조회 로직입니다.")
    public ApiResponse<?> getAllTemplate(){

        List<TemplateAllQueryDTO> queryDTO = templateQueryService.getAllTemplate();
        return ResponseUtil.successResponse("온보딩 스토리 보드가 성공적으로 조회되었습니다.", queryDTO).getBody();
    }
}
