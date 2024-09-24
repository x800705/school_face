package com.example.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.Sick;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SickMapper extends BaseMapper<Sick> {

    @Insert("INSERT INTO sick (stu_id,name,classes,start,end,reason) VALUES (#{sick.stu_id},#{sick.name},#{sick.classes},#{sick.start},#{sick.end},#{sick.reason})")
    int insert_data(Sick sick);
}
