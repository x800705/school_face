package com.example.sc.service;

import com.example.sc.entity.FoodType;
import com.example.sc.mapper.FoodTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodTypeService {

    @Autowired
    FoodTypeMapper foodTypeMapper;


    public List<FoodType> get_all(){
        return foodTypeMapper.get_all();
    }
}
