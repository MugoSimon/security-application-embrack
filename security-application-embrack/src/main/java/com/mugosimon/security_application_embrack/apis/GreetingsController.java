package com.mugosimon.security_application_embrack.apis;

import com.mugosimon.security_application_embrack.jwt.JwtUtils;
import com.mugosimon.security_application_embrack.model.LoginRequest;
import com.mugosimon.security_application_embrack.model.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The GreetingsController class is a REST controller that provides endpoints for user authentication
 * and access control using JWT. It includes methods for user login and role-based access to different
 * greetings. The controller uses Spring Security annotations to restrict access to specific roles.
 *
 * Endpoints:
 * - /guys: Accessible by users with roles USER or ADMIN.
 * - /user: Accessible by users with role USER.
 * - /admin: Accessible by users with role ADMIN.
 * - /signIn: Authenticates a user and returns a JWT along with user details and roles.
 */


@Slf4j
@RestController
public class GreetingsController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/guys")
    public String helloGuys() {
        log.info("guys accessed this method");
        return "Hello, World!";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/user")
    public String helloUser() {
        log.info("authorized user accessed this method");
        return "Hello, User!";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    public String helloAdmin() {
        log.info("admin accessed this method");
        return "Hello, Admin: !";
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException authenticationException) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad Credentials.");
            map.put("status", "FALSE");

            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        LoginResponse response = new LoginResponse(jwtToken, userDetails.getUsername(), roles);

        return ResponseEntity.ok(response);
    }

}