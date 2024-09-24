package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Student {
    public String id;
    public String imgSrc;
    public String name;
    @TableField(select = false)
    public String pwd;
    public String idCard;
    public String classes;
    public int state; //是否在学校状态
    @TableField(exist = false)
    CheckIn checkIn;
}
