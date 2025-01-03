package spring.hi_hello_spring.onboarding.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.onboarding.command.application.dto.ReqOnboardingStatusDTO;
import spring.hi_hello_spring.onboarding.command.application.service.OnboardingStatusService;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.OnboardingStatus;

@RestController
@RequestMapping("api/v1/")
@RequiredArgsConstructor
@Tag(name = "Onboarding API", description = "온보딩 관련 API")
public class OnboardingStatusController {

    private final OnboardingStatusService onboardingStatusService;

    @Operation(summary = "(멘티) 온보딩 수행 상태 변경", description = "온보딩 수행 상태 변경 로직입니다.")
    @PutMapping("/mentee/onboarding/template/{templateSeq}/status")
    public ApiResponse<?> updateOnboardingStatus(@PathVariable Long templateSeq) {

        onboardingStatusService.updateOnboardingStatus(templateSeq);
        return ResponseUtil.successResponse("온보딩 수행 상태가 성공적으로 수정되었습니다.").getBody();
    }

    @Operation(summary = "(멘토) 온보딩 수행 상태 변경", description = "온보딩 수행 상태 변경 로직입니다.")
    @PutMapping("/mentor/onboarding/template/{templateSeq}/status")
    public ApiResponse<?> updateOnboardingStatusByMentor(@PathVariable Long templateSeq) {

        onboardingStatusService.updateOnboardingStatusByMentor(templateSeq);
        return ResponseUtil.successResponse("온보딩 수행 상태가 성공적으로 수정되었습니다.").getBody();
    }
}
