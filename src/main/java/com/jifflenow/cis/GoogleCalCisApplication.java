package com.jifflenow.cis;

import com.jifflenow.cis.services.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GoogleCalCisApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoogleCalCisApplication.class, args);
        System.out.println("Getting ready");
    }

}
