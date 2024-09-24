package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Business {

    private String id;
    @TableField(select = false)
    private String pwd;
    private String name;
    private String shopName;
    private String phone;




}
