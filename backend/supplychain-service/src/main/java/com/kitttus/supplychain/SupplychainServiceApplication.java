package com.kitttus.supplychain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.kitttus")
public class SupplychainServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SupplychainServiceApplication.class, args);
    }
}
