package com.example.sc.mapper;

import com.example.sc.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zls
 */
@Mapper
public interface UserMapper {



    /**
     * 查询单条
     *
     * @param name
     * @return
     */

    //查询是否有用户名存在
    @Select("select * from user where name = #{name}")
    User is_sign(@Param("name") String name);


    @Select("select * from user where name = #{name} and pwd = #{pwd}")
    User login(@Param("name") String name, @Param("pwd") String pwd);

    @Insert("insert into user values(#{name},#{pwd},0)")
    int register(@Param("name")String name, @Param("pwd") String pwd);
}
