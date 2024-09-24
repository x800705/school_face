package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CheckIn {
    private String id;
    private String name;
    private String classes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private Integer state;
    @TableField(exist = false)
    private Integer page;
    @TableField(exist = false)
    private Integer size;

}
