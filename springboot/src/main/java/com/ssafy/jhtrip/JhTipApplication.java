package com.ssafy.jhtrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy"})
public class JhTipApplication {

    public static void main(String[] args) {
        SpringApplication.run(JhTipApplication.class, args);
    }

}
