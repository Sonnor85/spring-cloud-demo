package hu.siz.spring.cloud.authentication.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class JwtTokenService {
    private static final long SHORT_TERM = Duration.of(5, MINUTES).toSeconds();
    private static final long LONG_TERM = Duration.of(30, DAYS).toSeconds();

    private Key key = Keys.hmacShaKeyFor("234fdssyxdSDFsdSDfSsdf_SDf.we2335tgdfgdfg".
            getBytes(StandardCharsets.ISO_8859_1));

    public String generateToken(String username) {
        return generateTokenInternal(username, SHORT_TERM);
    }

    public String generateRefreshToken(String username) {
        return generateTokenInternal(username, LONG_TERM);
    }

    private String generateTokenInternal(String username, long validityTime) {
        return Jwts.builder().
                setSubject(username).
                setIssuedAt(new Date()).
                setExpiration(Date.from(ZonedDateTime.now().plusSeconds(validityTime).toInstant())).
                signWith(key).
                compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
