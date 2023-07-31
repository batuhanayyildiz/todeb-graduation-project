package com.todeb.batuhanayyildiz.creditapplicationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.Clock;


@SpringBootApplication
public class CreditApplicationSystemApplication {


    public static void main(String[] args) {
        SpringApplication.run(CreditApplicationSystemApplication.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}
