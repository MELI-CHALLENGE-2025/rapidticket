package com.rapidticket.auth.utils;

import org.springframework.beans.factory.annotation.Value;
import com.rapidticket.auth.utils.enums.EnumRoleUser;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.*;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenUtils {
    private final String secret;
    private final long expiration;
    private Key key;

    public JwtTokenUtils(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.expiration}") long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    @PostConstruct
    public void init() {
        byte[] secretBytes = Base64.getDecoder().decode(secret);
        this.key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(String subject, List<EnumRoleUser> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        List<String> roleNames = roles.stream()
                .map(EnumRoleUser::name)
                .toList();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .addClaims(Map.of("roles", roleNames))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
