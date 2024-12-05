package spring.hi_hello_spring.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.common.util.RedisService;
import spring.hi_hello_spring.employee.command.application.service.EmployeeService;
import spring.hi_hello_spring.employee.command.domain.aggregate.entity.Employee;
import spring.hi_hello_spring.employee.command.domain.repository.EmployeeRepository;
import spring.hi_hello_spring.security.entity.CustomUserDetails;
import spring.hi_hello_spring.security.service.CustomUserDetailsService;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final CustomUserDetailsService userDetailsService;
    private final RedisService redisService;
    private final EmployeeService employeeService;

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final EmployeeRepository employeeRepository;


    public JwtUtil(
            @Value("${TOKEN_SECRET}") String secretKey,
            CustomUserDetailsService userDetailsService, RedisService redisService, EmployeeService employeeService,
            @Qualifier("employeeRepository") EmployeeRepository employeeRepository) {
        this.redisService = redisService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    /* accessToken 검증(Bearer 토큰이 넘어왔고, 우리 사이트의 secret key로 만들어 졌는가, 만료되었는지와 내용이 비어있진 않은지) */
    public boolean validateAccessToken(String accessToken) {

        accessToken = accessToken.replaceAll("\\s+", "");
        System.out.println("엑세스 토큰 " + accessToken);

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Token claims empty {}", e);
        }
//        catch (Exception e) {
//            return false;
//        }
        return false;
    }

    /* refreshToken 검증(Bearer 토큰이 넘어왔고, 우리 사이트의 secret key로 만들어 졌는가, 만료되었는지와 내용이 비어있진 않은가
     *  ,레디스에 저장된 토큰과 동일한가, 내부의 담긴 값은 일치하는가) */
    public boolean validateRefreshToken(String refreshToken) {

        String savedToken = "";

        try {
            // 1차 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);

            // 요청 토큰이 담고있던 사원Seq
            String EmployeeSeqInRefreshToken = getEmployeeSeq(refreshToken);

            // 레디스에 저장된 해당 사원이 이전에 발급 받았던 리프레시 토큰 조회
            savedToken = redisService.getToken(EmployeeSeqInRefreshToken);
            if (savedToken != null) {
                // 저장되어 있던 토큰의 담긴 값
                String EmployeeSeqInRedisToken = getEmployeeSeq(savedToken);
                if (refreshToken.equals(savedToken) && EmployeeSeqInRefreshToken.equals(EmployeeSeqInRedisToken)) {
                    return true;
                }
            }

        } catch (Exception e) {
            log.info("Invalid JWT Token {}", e);
            // 로그아웃 처리 및 레디스 내의 토큰 삭제
            employeeService.logout(null);
            return false;
        }
        return false;
    }

    /* Token 에서 Claims 추출 */
    public Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /* Token 에서 사용자의 seq(subject 클레임) 추출 */
    public String getEmployeeSeq(String token) {
        return parseClaims(token).getSubject();
    }

    // accessToken 생성
    public String generateAccessToken(String employeeNum, Authentication authentication) {

        Employee employee = employeeRepository.findByEmployeeNum(employeeNum)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND)); // 예외 처리 어찌할건지

        Long employeeSeq = employee.getEmployeeSeq();
        long expirationTime = (long) 1000 * 60 * 30; // 30분

        Claims claims = Jwts.claims().setSubject(String.valueOf(employeeSeq));
        claims.put("auth", authentication.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    // refreshToken 생성
    public String generateRefreshToken(String employeeNum) {

        Employee employee = employeeRepository.findByEmployeeNum(employeeNum)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND)); // 예외 처리 어찌할건지

        Long employeeSeq = employee.getEmployeeSeq();

        long expirationTime = (long) 1000 * 60 * 60 * 24 * 7; // 7일
        Claims claims = Jwts.claims().setSubject(String.valueOf(employeeSeq));

        // 만료 시간 설정
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // 만료 시간
                .signWith(key)
                .compact();
    }

    // request 에서 refresh token or access token 을 추출
    public Optional<String> getToken(HttpServletRequest request, String tokenType) {

        String authorizationHeader;
        if (tokenType.equals("refresh")) authorizationHeader = request.getHeader("refreshToken");
        else authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return Optional.of(authorizationHeader.substring(7));
        }
        return Optional.empty();
    }

    // authentication 저장
    public void saveAuthentication(Long employeeSeq) {
        System.out.println("employeeSeq: " + employeeSeq);
        CustomUserDetails customUserDetails = userDetailsService.loadUserBySeq(employeeSeq);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(customUserDetails, null,
                        authoritiesMapper.mapAuthorities(customUserDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // 엑세스 토큰 재발급
    public String reIssueAccessToken(String refreshToken) {
        // 리프레시 토큰에서 사용자 employeeSeq 추출
        Long employeeSeq = Long.parseLong(getEmployeeSeq(refreshToken));
        Employee findUser = employeeRepository.findByEmployeeSeq(employeeSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        // 새로운 액세스 토큰 생성
        CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(findUser.getEmployeeNum());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(customUserDetails, null,
                        authoritiesMapper.mapAuthorities(customUserDetails.getAuthorities()));

        return generateAccessToken(getEmployeeSeq(refreshToken), authentication);
    }

    // 생성된 토큰 레디스에 저장
        public void saveToken(String Token) {

        String employeeSeq = getEmployeeSeq(Token);
        // JWT 디코딩하여 만료 시간 가져오기
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(Token)
                .getBody();

        // 만료 시간 (exp는 Unix timestamp 형식)
        long exp = claims.getExpiration().getTime() / 1000;  // 초 단위로 변환

        // 현재 시간과 만료 시간을 비교하여 TTL 계산
        long ttl = exp - (System.currentTimeMillis() / 1000);
        redisService.saveToken(employeeSeq, Token, ttl);
    }

}
