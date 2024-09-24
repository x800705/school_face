package com.example.sc.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Admin {
    private String id;
    @TableField(select = false)
    private String pwd;
    private String name;
    private String pro;


}
