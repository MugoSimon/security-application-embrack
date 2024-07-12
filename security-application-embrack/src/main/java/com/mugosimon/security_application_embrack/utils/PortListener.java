package com.mugosimon.security_application_embrack.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * The PortListener class listens for the initialization of the web server and captures the port number
 * on which the server is running. This class implements the ApplicationListener interface to handle
 * WebServerInitializedEvent events. It provides a method to retrieve the captured port number.
 *
 * Fields:
 * - port: An integer representing the port number on which the web server is running.
 *
 * Methods:
 * - onApplicationEvent: Captures the port number from the WebServerInitializedEvent and stores it in the port field.
 * - getPort: Returns the captured port number.
 *
 * Annotations:
 * - @Slf4j: Enables logging within the class.
 * - @Component: Marks the class as a Spring component for automatic detection and registration.
 */


@Slf4j
@Component
public class PortListener implements ApplicationListener<WebServerInitializedEvent> {

    private int port;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }

    public int getPort() {
        return port;
    }
}
