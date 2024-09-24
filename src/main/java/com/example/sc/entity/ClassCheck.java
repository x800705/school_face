package com.example.sc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ClassCheck {
    private int id;
    private String classId;
    private String stuId;
    private int checks;
    @TableField(exist = false)
    private Student student;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date time;
    @TableField(exist = false)
    private Classes classes;

}
