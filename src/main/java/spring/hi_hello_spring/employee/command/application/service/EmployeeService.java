package spring.hi_hello_spring.employee.command.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.common.util.RedisService;
import spring.hi_hello_spring.security.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final RedisService redisService;
    private final JwtUtil jwtUtil;

    public void logout(String accessToken) {

        String EmployeeSeq = String.valueOf(CustomUserUtils.getCurrentEmployeeSeq());

        redisService.deleteToken(EmployeeSeq);

        if (accessToken != null) {
            jwtUtil.saveToken(accessToken);
        }

    }
}
