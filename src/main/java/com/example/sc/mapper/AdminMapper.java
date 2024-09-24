package com.example.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.Admin;
import com.example.sc.entity.Student;
import com.example.sc.entity.sm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {



    @Select("select * from admin where id = #{id} and pwd = #{pwd}")
    Admin login(@Param("id") String id, @Param("pwd") String pwd);


    @Select("select * from admin where id = #{id}")
    Admin find_name(@Param("id") String id);


    @Select("select * from admin")
    List<Admin> find_all_admin();

    @Delete("delete from admin where id = #{id}")
    void delete_admin(@Param("id") String id);

    @Insert("insert into admin values(#{id},#{name},#{pwd})")
    void add_admin(@Param("id") String id,@Param("name") String name,@Param("pwd") String pwd);

    @Update("update admin set name = #{name} , pwd = #{pwd} where id = #{id}")
    void change_admin(@Param("id") String id,@Param("name") String name,@Param("pwd") String pwd);

    @Select("select * from admin where id = #{id} and pwd = #{pwd}")
    Admin logins(Admin admin);




    @Select("SELECT * from admin where id like concat(#{content},'%') limit #{start},#{size}")
    List<Admin> search_by_id(@Param("start") int start, @Param("size") int size, @Param("content") String content);

    @Select("SELECT * from student where name like concat(#{content},'%') limit #{start},#{size}")
    List<Admin> search_by_name(@Param("start") int start, @Param("size") int size, @Param("content") String content);


    @Select("select count(*) from admin ")
    int count();


    @Delete("delete from admin where id = #{id}")
    int del(@Param("id") String id);

    @Update("update admin set name=#{name},pro=#{pro},pwd=#{pwd} where id=#{id}")
    int change(@Param("id") String id,@Param("name") String name,@Param("pro") String pro,@Param("pwd") String pwd);

    @Insert("insert into admin values(#{id},#{name},#{pro},#{pwd})")
    int add(@Param("id") String id,@Param("name") String name,@Param("pro") String pro,@Param("pwd") String pwd);

    @Select("select * from admin where phone = #{phone}")
    List<Admin> is_phone(@Param("phone") String phone);


    Admin new_login(Admin admin);

    @Update("update admin set pwd = #{pwd} where id = #{id}")
    int change_pwd(Admin admin);
}
