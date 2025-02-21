package com.rapidticket.register.utils.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Base64;

@Component
@Slf4j
public class JwtTokenUtils {

    private final String secret;
    private Key key;

    public JwtTokenUtils(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @PostConstruct
    public void init() {
        byte[] secretBytes = Base64.getDecoder().decode(secret);
        this.key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("Token no soportado: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Token mal formado: {}", e.getMessage());
        } catch (io.jsonwebtoken.security.SignatureException e) {
            log.warn("Firma no válida: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Token vacío o inválido: {}", e.getMessage());
        }
        return false;
    }
}