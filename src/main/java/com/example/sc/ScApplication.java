package com.example.sc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.example.sc.mapper")
@ServletComponentScan
public class ScApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScApplication.class, args);
    }

}


