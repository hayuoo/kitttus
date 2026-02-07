package com.kitttus.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private final SecretKey signingKey;
    private final long expirationSeconds;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String base64Secret,
            @Value("${security.jwt.expiration-seconds:900}") long expirationSeconds) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
        this.expirationSeconds = expirationSeconds;
    }

    public String createToken(String subject, Collection<String> roles) {
        return createToken(subject, roles, Map.of());
    }

    public String createToken(String subject, Collection<String> roles, Map<String, Object> extraClaims) {
        Instant now = Instant.now();
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("roles", roles);
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationSeconds)))
                .signWith(signingKey)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
    }

    public Collection<String> extractRoles(String token) {
        var claims = parse(token);
        var rawRoles = claims.get("roles", Collection.class);
        return rawRoles.stream().map(String::valueOf).collect(Collectors.toSet());
    }
}
