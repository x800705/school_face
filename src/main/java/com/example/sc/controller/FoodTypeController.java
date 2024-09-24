package com.example.sc.controller;

import com.example.sc.entity.Food;
import com.example.sc.entity.FoodType;
import com.example.sc.mapper.FoodTypeMapper;
import com.example.sc.service.FoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/type")
public class FoodTypeController {

    @Autowired
    FoodTypeService foodTypeService;

    @Autowired
    FoodTypeMapper foodTypeMapper;

    @GetMapping("/get_all")
    public List<FoodType> get_all(){
        return foodTypeService.get_all();
    }



    @PostMapping("/add")
    public int add(@RequestBody FoodType foodType){
        return foodTypeMapper.insert(foodType);
    }

    @PostMapping("/del/{id}")
    public int del(@PathVariable int id){
        return foodTypeMapper.deleteById(id);
    }

    @PostMapping("/change")
    public int change(@RequestBody FoodType foodType){

        return foodTypeMapper.updateById(foodType);
    }



}
