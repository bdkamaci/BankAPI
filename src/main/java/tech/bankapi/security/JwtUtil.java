package tech.bankapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${JWT_SECRET:default-secret-key}")
    private String secretKey;

    @Value("${JWT_EXPIRATION_TIME:900000}") // Default to 15 minutes
    private long expirationTime;

    private final RedisTemplate<String, String> redisTemplate;

    @SuppressWarnings("unused")
    private static final long ACCESS_TOKEN_VALIDITY = 900000;  // 15 minutes

    @SuppressWarnings("unused")
    private static final long REFRESH_TOKEN_VALIDITY = 604800000; // 7 days

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String email, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId) // Add user ID
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails, LocalDateTime lastLogoutTime) {
        if (redisTemplate.hasKey(token).equals(Boolean.TRUE)) {
            return false;  // Token is revoked
        }
        String username = extractUsername(token);
        Date issuedAt = extractClaim(token, Claims::getIssuedAt);
        boolean isTokenValidAfterLogout = issuedAt.toInstant().isAfter(lastLogoutTime.toInstant(ZoneOffset.UTC));

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) && isTokenValidAfterLogout);
    }

    public void revokeToken(String token) {
        long expirationMillis = extractExpiration(token).getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set(token, "revoked", expirationMillis, TimeUnit.MILLISECONDS);
    }
}
