package com.example.commercebackoffice;

import com.example.commercebackoffice.common.security.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommerceBackOfficeApplication {

    private static PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(CommerceBackOfficeApplication.class, args);


        String encoded = passwordEncoder.encode("Rlacodnjs12#");
        System.out.println(encoded);
    }

}
