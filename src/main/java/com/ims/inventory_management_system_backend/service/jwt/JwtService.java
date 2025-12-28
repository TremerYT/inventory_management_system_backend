package com.ims.inventory_management_system_backend.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

    private static final String TOKEN_PREFIX = "Bearer ";

    private String generateToken(Authentication authentication, long expirationTime, Map<String, String> claims) {
        UserDetails userprincipal = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(userprincipal.getUsername())
                .setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    public String generateAccessToken (Authentication authentication) {
        return generateToken(authentication, expiryTime, new HashMap<>());
    }

    public String genareteRefreshToken(Authentication authentication) {
        Map<String, String> claims = new HashMap<>();
        claims.put("tokenType", "refreshToken");

        return generateToken(authentication, refreshExpiration, claims);
    }

    public Boolean validateAccessToken (String token, UserDetails userDetails) {
        final String username = extractUserName(token);

        if (!username.equals(userDetails.getUsername())){
            return false;
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJwt(token);
            return true;
        } catch (ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("JWT is Malformed: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty: {}", e.getMessage());
        } catch (SignatureException e){
            log.error("Invalid Jwt Signature");
        }

        return false;
    }

    public Boolean isRefreshToken (String token) {
        final String tokenType = extractTokenType(token);
        return "refreshToken".equals(tokenType);
    }

    private <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName (String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String extractTokenType (String token){
        return extractClaim(token, claims -> claims.get("tokenType", String.class));
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
