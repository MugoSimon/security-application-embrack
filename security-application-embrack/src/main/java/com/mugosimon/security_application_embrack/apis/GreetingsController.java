package com.mugosimon.security_application_embrack.apis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GreetingsController {

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
}