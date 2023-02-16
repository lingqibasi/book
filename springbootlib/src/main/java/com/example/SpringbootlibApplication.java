package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication()
public class SpringbootlibApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootlibApplication.class, args);
    }

    @GetMapping("/")
    public String health(){
        return "ok";
    }


}
