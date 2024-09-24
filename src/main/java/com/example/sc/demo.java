package com.example.sc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo {
    @GetMapping("/")
    public String index(){
        return "Hello,World";
    }

    @GetMapping("/ji")
    public String cxk(){
        return "鸡你太美";
    }

}
