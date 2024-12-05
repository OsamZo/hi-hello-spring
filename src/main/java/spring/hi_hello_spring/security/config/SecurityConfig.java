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

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz ->
                        // 추후 api 명세서 작성 후 추가 예정
//                        authz.requestMatchers(new AntPathRequestMatcher("/users/**", "POST")).permitAll()
                        authz.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                                .anyRequest().authenticated() // 나머지 요청은 필터를 거쳐야한다.
                )

                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

//        http.addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

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

}
