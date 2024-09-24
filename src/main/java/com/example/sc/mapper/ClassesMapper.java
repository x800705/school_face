package com.example.sc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.ClassCheck;
import com.example.sc.entity.Classes;
import com.example.sc.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassesMapper extends BaseMapper<Classes> {

    @Select("SELECT * FROM classes WHERE checks = 1")
    List<Classes> is_check();


    @Update("update classes set checks = #{checks} where id = #{id}")
    int change_check(@Param("id") String id,@Param("checks") String checks);

    @Select("select * from class_check where class_id = #{id}")
    List<ClassCheck> is_exsit(String id);

    @Select("select * from student where classes = #{classes}")
    List<Student> find_student(String classes);

    @Insert("insert into class_check (class_id,stu_id) values (#{classes_id},#{stu_id})")
    int add_list(@Param("stu_id") String stu_id,@Param("classes_id") String classes_id);

    @Select("select count(*) from class_check where class_id = #{classes_id}")
    int total(String classes_id);

    @Select("select * from class_check where class_id = #{class_id} and checks = #{checks} limit #{page},#{size}")
    @Results({

            @Result(column = "id",property = "id"),
            @Result(column = "classId",property = "class_id"),
            @Result(column = "stuId",property = "stu_id"),
            @Result(column = "checks",property = "checks"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "stu_id",property = "student",one=@One(select = "com.example.sc.mapper.StudentMapper.get_student"))
    })
    List<ClassCheck> page_list(String class_id,String checks, int page, int size);



    @Update("update class_check set checks = 1,time=#{time} where stu_id = #{stu_id} and class_id = #{class_id}")
    int stu_check(String class_id,String stu_id,String time);

    @Delete("delete from classes where id = #{id}")
    int del(String id);

    @Select("select * from classes where id = #{id}")
    Classes get_class(String id);

    @Select("select * from class_check where stu_id = #{id} limit #{page},10")
    @Results({

            @Result(column = "classId",property = "class_id"),
            @Result(column = "stuId",property = "stu_id"),
            @Result(column = "checks",property = "checks"),
            @Result(column = "time",property = "time"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "class_id",property = "classes",one=@One(select = "com.example.sc.mapper.ClassesMapper.get_class"))
    })
    List<ClassCheck> stu_find(String id,int page);


    @Select("select count(*) from class_check where class_id=#{class_id} and checks=#{checks}")
    int page_total(String class_id, String checks);
}
