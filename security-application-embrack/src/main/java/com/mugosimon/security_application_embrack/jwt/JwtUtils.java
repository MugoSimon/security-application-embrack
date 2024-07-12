package com.mugosimon.security_application_embrack.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


/**
 * The JwtUtils class provides utility methods for handling JSON Web Tokens (JWT) in the application.
 * It includes methods for extracting JWTs from HTTP headers, generating JWTs, validating JWTs,
 * and retrieving the username from JWTs. The class uses the io.jsonwebtoken library for JWT operations.
 *
 * Methods:
 * - getJwtFromHeader: Extracts the JWT from the Authorization header of an HTTP request.
 * - generateTokenFromUsername: Generates a JWT for a given UserDetails object.
 * - getUsernameFromJwtToken: Retrieves the username from a given JWT.
 * - validateJwtToken: Validates a given JWT.
 * - key: Generates a signing key from the configured JWT secret.
 * - showError: Logs error messages along with exceptions.
 */


@Slf4j
@Component
public class JwtUtils {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (bearerToken == null || bearerToken.isEmpty()) {
            log.warn("Authorization header is missing or empty");
            return null;
        }

        log.info("Authorization Header: {}", bearerToken);

        if (!bearerToken.startsWith(BEARER_PREFIX)) {
            log.warn("Authorization header does not start with Bearer");
            return null;
        }

        try {
            return bearerToken.substring(BEARER_PREFIX.length()).trim();
        } catch (Exception e) {
            showError("Error extracting JWT from header", e);
            return null;
        }
    }

    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (MalformedJwtException e) {
            showError("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            showError("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            showError("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            showError("JWT claims string is empty", e);
        } catch (Exception e) {
            showError("JWT parsing error", e);
        }
        return null;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            log.info("Validating JWT token");
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            showError("Invalid JWT signature", e);
        } catch (MalformedJwtException e) {
            showError("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            showError("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            showError("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            showError("JWT claims string is empty", e);
        } catch (Exception e) {
            showError("JWT validation error", e);
        }
        return false;
    }


    private void showError(String message, Exception e) {
        log.error("{}: {}", message, e.getMessage(), e);
    }
}