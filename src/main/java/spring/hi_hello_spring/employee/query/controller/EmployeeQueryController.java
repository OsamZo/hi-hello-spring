package spring.hi_hello_spring.employee.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.employee.query.dto.*;
import spring.hi_hello_spring.employee.query.service.EmployeeQueryService;
import spring.hi_hello_spring.employee.query.dto.DepartmentListDTO;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "사원 관련 API")
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;

    @GetMapping("/hr/mentee")
    @Operation(summary = "멘티 전체 조회", description = "멘티 전체 조회 로직 입니다.")
    public ApiResponse<?> getAllMentee() {

        List<MenteeAllQueryDTO> menteeAllQueryDTO = employeeQueryService.getAllMentee();
        return ResponseUtil.successResponse("멘티 전체 조회가 성공적으로 조회되었습니다.", menteeAllQueryDTO).getBody();
    }

    @GetMapping("/hr/mentee/{departmentSeq}")
    @Operation(summary = "부서별 멘토 없는 멘티 조회", description = "부서별 멘티 조회 로직 입니다.")
    public ApiResponse<?> getEmployeeByDepartmentSeq( @PathVariable Long departmentSeq) {

        List<MenteeDepQueryDTO> menteeDepQueryDTO = employeeQueryService.getDepMentees(departmentSeq);
        return ResponseUtil.successResponse("부서별 멘티 조회가 성공적으로 조회되었습니다.", menteeDepQueryDTO).getBody();
    }

    @GetMapping("/hr/task/mentee/{departmentSeq}")
    @Operation(summary = "부서별 전체 멘티 조회", description = "부서별 멘티 조회 로직 입니다.")
    public ApiResponse<?> getAllMenteeByDepartmentSeq( @PathVariable Long departmentSeq) {

        List<MenteeDepQueryDTO> menteeDepQueryDTO = employeeQueryService.getAllDepMentees(departmentSeq);
        return ResponseUtil.successResponse("부서별 전체 멘티 조회가 성공적으로 조회되었습니다.", menteeDepQueryDTO).getBody();
    }

    @GetMapping("/hr/mentor")
    @Operation(summary = "멘토 전체 조회", description = "멘토 전체 조회 로직 입니다.")
    public ApiResponse<?> getAllMentor() {
        List<MentorAllQueryDTO> mentorAllQueryDTO = employeeQueryService.getAllMentor();
        return ResponseUtil.successResponse("멘토 전체 조회가 성공적으로 조회되었습니다.", mentorAllQueryDTO).getBody();
    }

    @GetMapping("/hr/mentor/{departmentSeq}")
    @Operation(summary = "부서별 멘토 조회", description = "부서별 멘토 조회 로직 입니다.")
    public ApiResponse<?> getMentorByDepartmentSeq( @PathVariable Long departmentSeq) {

        List<MentorDepQueryDTO> mentorDepQueryDTO = employeeQueryService.getDepMentors(departmentSeq);
        return ResponseUtil.successResponse("부서별 멘토 조회가 성공적으로 조회되었습니다.", mentorDepQueryDTO).getBody();
    }

    @Operation(summary = "내 프로필 조회", description = "사원은 본인의 프로필을 조회한다.")
    @GetMapping("/employee/info")
    public ApiResponse<?> getEmployeeInfo() {
        ResEmplInfoQueryDTO reqEmployeeInfoDTO = employeeQueryService.getEmployeeInfo();
        return ResponseUtil.successResponse("내 정보가 성공적으로 조회되었습니다.", reqEmployeeInfoDTO).getBody();
    }

    @Operation(summary = "멘토 프로필 조회", description = "멘티가 멘토의 프로필을 조회한다.")
    @GetMapping("/mentee/mentor/info")
    public ApiResponse<?> getMenteeInfo() {
        ResEmplInfoQueryDTO reqEmployeeInfoDTO = employeeQueryService.getMentorInfo();
        return ResponseUtil.successResponse("멘토의 프로필 정보가 성공적으로 조회되었습니다.", reqEmployeeInfoDTO).getBody();
    }

    @Operation(summary = "멘티 프로필 조회", description = "멘토가 멘티의 프로필을 조회한다.")
    @GetMapping("mentor/mentee/info")
    public ApiResponse<?> getMentorInfo() {
        ResEmplInfoQueryDTO reqEmployeeInfoDTO = employeeQueryService.getMenteeInfo();
        return ResponseUtil.successResponse("멘티의 프로필 정보가 성공적으로 조회되었습니다.", reqEmployeeInfoDTO).getBody();
    }

    @Operation(summary = "사원 리스트 조회", description = "담당자는 사원 리스트를 조회한다.")
    @GetMapping("/hr/user")
    public ApiResponse<?> getEmployeeAll() {
        List<EmployeeListDTO> employees = employeeQueryService.getEmployeeAll();
        return ResponseUtil.successResponse("사원 리스트를 조회하였습니다.", employees).getBody();
    }

    @Operation(summary = "사원 검색", description = "담당자는 사원을 검색하여 조회한다.")
    @GetMapping("/hr/user/search")
    public ApiResponse<?> getEmployeeSearchQuery(@RequestParam String searchType, @RequestParam String keyword) {

        String decodedWord = null;
        try {
            decodedWord = URLDecoder.decode(keyword, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        List<EmployeeListDTO> employees = employeeQueryService.getEmployeeSearch(searchType, decodedWord);
        return ResponseUtil.successResponse("성공적으로 사원을 검색하였습니다.", employees).getBody();
    }

    @Operation(summary = "사원 이름 조회", description = "사원의 이름을 조회한다.")
    @GetMapping("/{employeeSeq}/name")
    public ApiResponse<?> getEmployeeName(@PathVariable Long employeeSeq) {

        String employeeName = employeeQueryService.getEmployeeName(employeeSeq);
        return ResponseUtil.successResponse("이름을 성공적으로 조회하였습니다.", employeeName).getBody();
    }

    @Operation(summary = "부서 조회", description = "부서를을 조회한다.")
    @GetMapping("/hr/department")
    public ApiResponse<?> getEmployeeDepartment() {

        List<DepartmentListDTO> departmentListDTOs = employeeQueryService.getAllDepartment();
        return ResponseUtil.successResponse("부서를 성공적으로 조회하였습니다.", departmentListDTOs).getBody();
    }
}
