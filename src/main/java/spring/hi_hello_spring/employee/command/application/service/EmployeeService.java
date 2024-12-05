package spring.hi_hello_spring.employee.command.application.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.hi_hello_spring.common.util.CustomUserUtils;
import spring.hi_hello_spring.common.util.RedisService;

import java.security.Key;

import static spring.hi_hello_spring.common.util.CustomUserUtils.getCurrentEmployeeSeq;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private Key key;
    private final RedisService redisService;
    private final CustomUserUtils customUserUtils;

    @Value("${token.secret}")
    private String secretKey;

    public void logout(String accessToken) {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        String EmployeeSeq = String.valueOf(getCurrentEmployeeSeq());
        accessToken = accessToken.substring(7);

        redisService.deleteToken(EmployeeSeq);

        if (accessToken != null) {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();

            // 만료 시간 (exp는 Unix timestamp 형식)
            long exp = claims.getExpiration().getTime() / 1000;  // 초 단위로 변환

            // 현재 시간과 만료 시간을 비교하여 TTL 계산
            long ttl = exp - (System.currentTimeMillis() / 1000);

            redisService.saveToken(EmployeeSeq + "a", accessToken, ttl);
        }

    }
}
