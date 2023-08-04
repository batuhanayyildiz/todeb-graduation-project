package com.todeb.batuhanayyildiz.creditapplicationsystem;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


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
