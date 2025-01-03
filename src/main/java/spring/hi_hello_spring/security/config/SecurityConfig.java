package spring.hi_hello_spring.security.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import spring.hi_hello_spring.security.filter.CustomAuthenticationFilter;
import spring.hi_hello_spring.security.filter.JwtFilter;
import spring.hi_hello_spring.security.handler.JwtAccessDeniedHandler;
import spring.hi_hello_spring.security.handler.JwtAuthenticationEntryPoint;
import spring.hi_hello_spring.security.handler.LoginFailureHandler;
import spring.hi_hello_spring.security.handler.LoginSuccessHandler;
import spring.hi_hello_spring.security.service.CustomUserDetailsService;
import spring.hi_hello_spring.security.util.JwtUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    private static final String[] SWAGGER_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/swagger-ui/index.html",
            "/swagger-ui/",
            "/v3/api-docs.yaml",
            "/",
            "/ws/**",
            "/api/v1/test/health",
            "/actuator/prometheus"
    };

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {


        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authz -> authz
                          .requestMatchers(SWAGGER_WHITE_LIST).permitAll()  // Swagger UI 접근 허용
                          .requestMatchers(
                                  "/api/v1/login"
                          ).permitAll()
                          .requestMatchers("/api/v1/test/health").permitAll()
                          .requestMatchers(new AntPathRequestMatcher("/mentee/**")).hasAuthority("MENTEE")
                          .requestMatchers(new AntPathRequestMatcher("/mentor/**")).hasAuthority("MENTOR")
                          .requestMatchers(new AntPathRequestMatcher("/hr/**")).hasAuthority("HR")
                          .anyRequest().authenticated() // 나머지 요청은 필터를 거쳐야한다.
                )

                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(getAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(
                exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(new JwtAccessDeniedHandler());
                    exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
                }
        );

        return http.build();
    }

    private Filter getAuthenticationFilter() {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
        customAuthenticationFilter.setAuthenticationManager(getAuthenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(jwtUtil));
        customAuthenticationFilter.setAuthenticationFailureHandler(new LoginFailureHandler());
        return customAuthenticationFilter;
    }

    private AuthenticationManager getAuthenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // 모든 도메인 허용 (필요시 특정 도메인 설정)
        configuration.addAllowedMethod("*");       // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*");       // 모든 헤더 허용
        configuration.setAllowCredentials(true);   // 쿠키 허용 (필요 시 true로 설정)
        configuration.addExposedHeader("accesstoken");
        configuration.addExposedHeader("refreshtoken");

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 적용
        return source;
    }
}
