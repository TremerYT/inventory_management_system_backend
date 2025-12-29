package com.ims.inventory_management_system_backend.service.jwt;

import com.ims.inventory_management_system_backend.dto.token.TokenPair;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long expiryTime;

    @Value("${jwt.refresh_expiration}")
    private long refreshExpiration;

    private String generateToken(
            Authentication authentication,
            long expirationTime,
            Map<String, Object> claims
    ) {
        UserDetails userprincipal = (UserDetails) authentication.getPrincipal();
        Date now = new Date();

        return Jwts.builder()
                .setSubject(userprincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationTime))
                .addClaims(claims)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, expiryTime, new HashMap<>());
    }

    public String genereteRefreshToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");

        return generateToken(authentication, refreshExpiration, claims);
    }

    public TokenPair generateTokenPair(Authentication authentication) {
        return new TokenPair(
                generateAccessToken(authentication),
                genereteRefreshToken(authentication)
        );
    }


    public Boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException e) {
            log.debug("Invalid JWT: {}", e.getMessage());
            return false;
        }
    }

    public boolean isTokenValidForUser(String token, UserDetails userDetails) {
        try {
            Claims claims = parseToken(token);
            return claims.getSubject().equals(userDetails.getUsername())
                    && !claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isRefreshToken(String token) {
        return "refresh".equals(
                extractClaim(token, c -> c.get("tokenType", String.class))
        );
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(parseToken(token));
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
