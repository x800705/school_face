package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Books {
    private String id;
    private String name;
    private String type;
    private double price;
    private String imgSrc;

    @TableField(exist = false)
    private int page;
    @TableField(exist = false)
    private int size;



}
