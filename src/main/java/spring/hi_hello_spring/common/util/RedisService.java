package spring.hi_hello_spring.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 리프레시 토큰을 디코딩하고 Redis 에 TTL 을 설정하여 저장
    public void saveToken(String employeeSeq, String Token, Long ttl) {
        
        redisTemplate.opsForValue().set(employeeSeq, Token, ttl, java.util.concurrent.TimeUnit.SECONDS);
    }

    // 리프레시 토큰 조회
    public String getToken(String employeeSeq) {
        return redisTemplate.opsForValue().get(employeeSeq);
    }

    // 리프레시 토큰 삭제
    public void deleteToken(String employeeSeq) {
        redisTemplate.delete(employeeSeq);
    }
}
