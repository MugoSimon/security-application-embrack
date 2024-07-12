package com.mugosimon.security_application_embrack.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * The AuthEntryPointJwt class is a component that implements AuthenticationEntryPoint.
 * It handles unauthorized access to protected resources by returning a JSON response
 * with an appropriate error message and status code. This class is used by Spring Security
 * to commence authentication when a user tries to access a secured REST endpoint without
 * being authenticated.
 *
 * Methods:
 * - commence: This method is triggered anytime unauthenticated access is attempted.
 *   It logs the unauthorized error and sends a JSON response with the HTTP status 401
 *   (UNAUTHORIZED), error message, and the request path.
 */


@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}",authException.getLocalizedMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "UNAUTHORIZED");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
