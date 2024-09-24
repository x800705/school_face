package com.example.sc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.Student;
import com.example.sc.entity.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Insert("insert into sm(id) values (#{id})")
    int create_money(String id);

    @Select("select * from teacher where id = #{id}")
    Teacher get_teacher(String id);

    @Select("select * from teacher")
    List<Teacher> find_imgsrc();

}

