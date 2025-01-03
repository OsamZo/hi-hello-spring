package spring.hi_hello_spring.group.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.group.command.application.dto.TaskRequestDTO;
import spring.hi_hello_spring.group.command.application.dto.TaskRequestWrapper;
import spring.hi_hello_spring.group.command.application.service.GroupMatchService;

import java.util.List;

@RestController
@RequestMapping("api/v1/hr/matching")
@RequiredArgsConstructor
@Tag(name = "Matching API", description = "매칭 관련 API")
public class GroupMatchController {

    private final GroupMatchService groupMatchService;

//    @PostMapping("/group")
//    @Operation(summary = "멘티 그룹 생성", description = "멘티 그룹 생성 로직입니다.")
//    public ApiResponse<?> createGroupMatch(@RequestBody TaskRequestWrapper wrapper) {
//
//        groupMatchService.createMenteeGroup(wrapper.getTasks());
//        return ResponseUtil.successResponse("멘티 그룹 생성이 성공적으로 등록되었습니다.").getBody();
//    }
}
