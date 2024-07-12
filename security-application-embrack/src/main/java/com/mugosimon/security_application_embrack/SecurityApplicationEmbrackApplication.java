package com.mugosimon.security_application_embrack;

import com.mugosimon.security_application_embrack.utils.PortListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * The SecurityApplicationEmbrackApplication class serves as the main entry point for the Security Embrack Service.
 * It is annotated with @SpringBootApplication to enable Spring Boot auto-configuration and component scanning.
 * <p>
 * Functionality:
 * - Starts the Spring Boot application using SpringApplication.run() in the main() method.
 * - Implements ApplicationListener<ApplicationReadyEvent> to handle the ApplicationReadyEvent after the application context is refreshed.
 * - Autowires PortListener to retrieve and display the port number on which the application is running.
 * - Prints a startup message including the port number when the application is ready.
 * <p>
 * Components and Annotations:
 * - @SpringBootApplication: Enables Spring Boot auto-configuration and component scanning.
 * - @Autowired: Injects an instance of PortListener to access the port number.
 * - @Override: Overrides the onApplicationEvent() method to handle the ApplicationReadyEvent.
 * - ApplicationListener<ApplicationReadyEvent>: Listens for the ApplicationReadyEvent to perform actions after the application context is initialized.
 */


@SpringBootApplication
public class SecurityApplicationEmbrackApplication implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private PortListener portListener;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplicationEmbrackApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        int port = portListener.getPort();
        System.out.println("\n***   ***   ***   ***   ***   ***   ***   ***   ***");
        System.out.println("Security Embrack Service Started\nrunning on port: " + port);
        System.out.println("***   ***   ***   ***   ***   ***   ***   ***   ***\n");
    }
}
