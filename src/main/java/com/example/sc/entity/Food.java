package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Food {
    @TableId(type = IdType.AUTO)
    private String id;
    private Double money;
    private String name;
    private String imgSrc;
    private int ban;
    private String type;
    private String content;
}
