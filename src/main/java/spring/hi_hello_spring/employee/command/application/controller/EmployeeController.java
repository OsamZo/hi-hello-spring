package spring.hi_hello_spring.employee.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.hi_hello_spring.common.response.ApiResponse;
import spring.hi_hello_spring.common.response.ResponseUtil;
import spring.hi_hello_spring.employee.command.application.service.EmployeeService;
import spring.hi_hello_spring.security.util.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
@Tag(name = "사원 관련 API")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "로그아웃", description = "사원이 로그아웃을 한다.")
    @PostMapping
    public ApiResponse<?> logout(HttpServletRequest request) {
        Optional<String> accessToken = jwtUtil.getToken(request, "access");
        employeeService.logout(accessToken.get());
        return ResponseUtil.successResponse("로그아웃을 완료하였습니다.").getBody();
    }

}
