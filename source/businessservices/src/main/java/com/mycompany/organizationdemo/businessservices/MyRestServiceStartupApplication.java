package com.mycompany.organizationdemo.businessservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/* the annotations drive this functionality */
@EnableWebSecurity
@SpringBootApplication
public class MyRestServiceStartupApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyRestServiceStartupApplication.class, args);
    }

}
