package com.mugosimon.security_application_embrack;

import com.mugosimon.security_application_embrack.utils.PortListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

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
        System.out.println("***   ***   ***   ***   ***   ***   ***   ***   ***");
        System.out.println("Security Embrack Service Started\nrunning on port: " + port);
        System.out.println("***   ***   ***   ***   ***   ***   ***   ***   ***");
    }
}
