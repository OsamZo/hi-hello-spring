package spring.hi_hello_spring.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.hi_hello_spring.employee.command.application.service.EmployeeService;
import spring.hi_hello_spring.security.util.JwtUtil;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final AntPathRequestMatcher[] SWAGGER_URLS = {
            new AntPathRequestMatcher("/"),
            new AntPathRequestMatcher("/login"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-resources/**")
    };

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        for (AntPathRequestMatcher url : SWAGGER_URLS) {
            if (url.matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        Optional<String> refreshToken = jwtUtil.getToken(request, "refresh");

        if (refreshToken.isPresent() && jwtUtil.validateRefreshToken(refreshToken.get())) {
            String newAccessToken = jwtUtil.reIssueAccessToken(refreshToken.get());
            String employeeSeq = jwtUtil.getEmployeeSeq(newAccessToken);
            String newRefreshToken = jwtUtil.generateRefreshToken(employeeSeq);

            response.setHeader("accessToken", newAccessToken);
            response.setHeader("refreshToken", newRefreshToken);

            // 로그용 코드
            log.info("accessToken {} ", newAccessToken);
            log.info("refreshToken {} ", newRefreshToken);

            jwtUtil.saveAuthentication(Long.parseLong(jwtUtil.getEmployeeSeq(newAccessToken)));

            // 재발급된 리프레시 토큰 레디스에 저장 (덮어쓰기)
            jwtUtil.saveToken(newRefreshToken);

            filterChain.doFilter(request, response);

            return;

        }

        Optional<String> accessToken = jwtUtil.getToken(request, "access");

        if (accessToken.isPresent()) {
            if (jwtUtil.validateAccessToken(accessToken.get())){
                jwtUtil.saveAuthentication(Long.parseLong(jwtUtil.getEmployeeSeq(accessToken.get())));
//                log.info("accessToken {} ", accessToken.get());
                filterChain.doFilter(request, response); // 다음 필터로 요청 전달
                return;
            }
        }

        // accessToken 이 유효하지 않은 경우 401 에러
        if (!response.isCommitted()) { // 응답이 커밋되지 않았을 때만 에러 전송
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid tokens");
        }

    }

}
