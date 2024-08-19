package br.edu.projeto_integrado.somar.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;
    private final LocalDateTime ACCESS_TOKEN_EXPIRATION = LocalDateTime.now().plusMinutes(10);
    private final LocalDateTime REFRESH_TOKEN_EXPIRATION = LocalDateTime.now().plusMinutes(30);

    public String generateAccessToken(UserDetails user, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .expiration(convertFromLocalDateTime(this.ACCESS_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(UserDetails user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(convertFromLocalDateTime(this.REFRESH_TOKEN_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenValid(String token, UserDetails user) {
        final var username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(secret.getBytes()));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T>T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date convertFromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
