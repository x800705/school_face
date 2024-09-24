package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class sm {
    private String id;
    private double money;
    private int ban;
    private Student student;
    private Teacher teacher;



}
