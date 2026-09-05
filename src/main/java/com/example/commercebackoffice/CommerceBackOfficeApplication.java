package com.example.commercebackoffice;

import com.example.commercebackoffice.common.security.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommerceBackOfficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceBackOfficeApplication.class, args);
    }

}
