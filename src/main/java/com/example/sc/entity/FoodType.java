package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class FoodType {
    private int id;
    private String type;
    @TableField(exist = false)
    private List<Food> food;

}
