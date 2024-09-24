package com.example.sc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.sc.service.FaceService;
import com.example.sc.entity.face;

@RequestMapping("/face")
public class FaceController {

    @Autowired
    private FaceService faceService;

    @GetMapping("/findOne/{id}")
    public face is_use(@PathVariable int id){
        return faceService.is_use(id);
    }

    @PostMapping("/pay")
    public Boolean pay(face f){
        return faceService.pay(f);
    }
}
