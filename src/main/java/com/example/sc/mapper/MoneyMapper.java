package com.example.sc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.User;
import com.example.sc.entity.sm;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface MoneyMapper extends BaseMapper<sm> {


    /**
     * 查询单条
     *
     * @param id
     * @return
     */

    @Select("select * from sm where id = #{id}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "ban",property = "ban"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "id",property = "student",one=@One(select = "com.example.sc.mapper.StudentMapper.get_student"))
    })
    sm find(@Param("id") String id);





    @Select("select * from sm join student on sm.id = student.id where student.id like concat(#{content},'%') limit #{start},#{size}")
    List<sm> search_by_id(@Param("start") int start, @Param("size") int size, @Param("content") String content);


    @Select("select * from sm join student on sm.id = student.id where name like concat(#{content},'%')limit #{start},#{size}")
    List<sm> search_by_name(int start, int size, String content);

    @Select("select * from sm join student on sm.id = student.id where classes like concat(#{content},'%') limit #{start},#{size}")
    List<sm> search_by_class(int start, int size, String content);


    @Select("select * from sm where ${methon} like concat(#{content},'%') limit #{start},#{size}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "ban",property = "ban"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "id",property = "student",one=@One(select = "com.example.sc.mapper.StudentMapper.get_student")),
            @Result(column = "id",property = "teacher",one=@One(select = "com.example.sc.mapper.TeacherMapper.get_teacher"))
    })
    List<sm> search(@Param("start") int start, @Param("size") int size, @Param("content") String content, @Param("methon") String methon);




    @Select("select count(*) from sm join student on sm.id = student.id")
    int count_stu();

    @Select("select count(*) from sm join teacher on sm.id = teacher.id")
    int count_tea();

    @Update("update sm set money = money + #{money} where id = #{id}")
    int add_money(String id, double money);

    @Insert("insert into consume value(#{id},#{money},#{date},#{content},(select money from sm where id = #{id}))")
    int add_consume(String id, double money, Date date, String content);

    @Update("update sm set money = #{money} where id = #{id}")
    int change_money(String id, double money);

    @Update("update sm set ban = 1 where id = #{id}")
    int ban(@Param("id") String id);


    @Update("update sm set ban = 0 where id = #{id}")
    int no_ban(@Param("id") String id);

    @Select("select * from consume where id = #{id} limit #{start},#{size}")
    List<sm> list(@Param("id") String id,@Param("start") int num,@Param("size") int size);

    @Select("select count(*) from consume where id = #{id}")
    int count_list(@Param("id") String id);


    @Update("update sm set money = money - #{money} where id = #{id}")
    int pay(String id, Double money);


    @Select("select * from sm where ban = 1 limit #{start},#{size}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "ban",property = "ban"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "id",property = "student",one=@One(select = "com.example.sc.mapper.StudentMapper.get_student")),


    })
    List<sm> lost(int start, int size);

    @Select("select count(*) from sm join student on sm.id = student.id where ban = 1")
    int count_lost();



    @Select("select * from sm join teacher on sm.id = teacher.id where ${methon} like concat(#{content},'%') limit #{start},#{size}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "ban",property = "ban"),
            //column 表示传入的参数 property表示实体类的名字
            @Result(column = "id",property = "teacher",one=@One(select = "com.example.sc.mapper.TeacherMapper.get_teacher"))
    })
    List<sm> search_tea(int start, int size, String content, String methon);



    @Select("select * from sm join student on sm.id = student.id where ${methon} like concat(#{content},'%') limit #{start},#{size}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(column = "money",property = "money"),
            @Result(column = "ban",property = "ban"),

            @Result(column = "id",property = "student",one=@One(select = "com.example.sc.mapper.StudentMapper.get_student")),

    })
    List<sm> search_stu(int start, int size, String content, String methon);

    @Select("select ban from sm where id = #{id}")
    int is_ban(String id);

    @Insert("insert into consume value(#{id},#{money},#{date},#{content},(select money from sm where id = #{id}))")
    int cost_consume(String id, double money, Date date, String content);

    @Insert("insert into sm (id) values(#{id})")
    int add_bank(String id);
}


