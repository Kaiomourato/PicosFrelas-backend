package com.PicosFrelas.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey; // Importação adicionada
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationInMs;

    // ALteração aqui: Retorna SecretKey em vez de Key
    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Gera token JWT
    public String generateToken(UUID userId) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);

        return Jwts.builder()
            .subject(String.valueOf(userId))
            .issuedAt(new Date())
            .expiration(expireDate)
            .signWith(key())
            .compact();
    }

    // Extrai o ID do usuário do token
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
            .verifyWith(key())
            .build()
            .parseSignedClaims(token)
            .getPayload();

        return claims.getSubject();
    }

    // Valida o token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            // Token malformado
        } catch (ExpiredJwtException e) {
            // Token expirado
        } catch (UnsupportedJwtException e) {
            // Tipo de token não suportado
        } catch (IllegalArgumentException e) {
            // Claims do token estão vazios
        }
        return false;
    }
}