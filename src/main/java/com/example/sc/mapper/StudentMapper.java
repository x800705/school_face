package com.example.sc.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.Student;
import com.example.sc.entity.consume;
import com.example.sc.entity.sm;
import org.apache.ibatis.annotations.*;

import java.lang.annotation.Inherited;
import java.util.Date;
import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {


    /**
     * 查询单条
     *
     * @param id
     * @return
     */
    @Select("select name from student where id = #{id}")
    Student findOne(String id);

    //学生登录
    @Select("select * from student where id = #{id} and pwd = #{pwd}")
    List<Student> login(@Param("id") String id ,@Param("pwd") String pwd);



    @Select("select name from student where id = #{id}")
    Student get_name(@Param("id") String id);


    @Select("select id,img_src,name,classes from student where id = #{id}")
    List<Student> get_student(@Param("id") String id);

    //获取所有学生
    @Select("select * from student")
    List<Student> findAll();


    @Insert("insert into student (id,name,classes,pwd) values (#{id},#{name},#{classes},#{pwd})")
    int add_student(@Param("id") String id,@Param("name") String name,@Param("classes") String classes,@Param("pwd") String pwd);


    @Update("update student set name=#{name},classes=#{classes},pwd=#{pwd} where id=#{id}")
    int change_student(@Param("id") String id,@Param("name") String name,@Param("classes") String classes,@Param("pwd") String pwd);

    @Select("select * from student limit #{start},#{end}")
    List<Student> find_student(@Param("start") int start,@Param("end") int end);

    @Select("select * from consume where id = #{id} limit #{start},#{end}")
    List<consume> find_student_consume(@Param("id") String id,@Param("start") int start,@Param("end") int end);

    @Update("update student set money = money + #{money} where id = #{id}")
    int add_money(@Param("id") String id,@Param("money") double money);

    @Update("update student set money = #{money} where id = #{id}")
    int change_money(@Param("id") String id,@Param("money") double money);

    @Insert("insert into consume value(#{id},#{money},#{date},#{content},(select money from student where id = #{id}))")
    int add_consume(@Param("id") String id, @Param("money") double money, @Param("date") Date date,@Param("content") String content);

    @Update("update student set ban = 1 where id = #{id}")
    int ban(@Param("id") String id);

    @Delete("delete from student where id = #{id}")
    int Delone(@Param("id") String id);

    @Update("update student set pwd=#{pwd} where id = #{id}")
    int up_pwd(@Param("id") String id ,@Param("pwd") String pwd);

    @Select("SELECT * FROM consume WHERE DATE LIKE concat(#{date},'%') and id = #{id} limit #{page},10")
    List<consume> find_students_consume(@Param("id") String id,@Param("date") String date,@Param("page") int page);

    @Select("SELECT * from student where id like concat(#{content},'%') limit #{start},#{size}")
    List<Student> search_by_id(@Param("start") int start, @Param("size") int size,@Param("content") String content);

    @Select("SELECT * from student where name like concat(#{content},'%') limit #{start},#{size}")
    List<Student> search_by_name(@Param("start") int start, @Param("size") int size,@Param("content") String content);

    @Select("SELECT * from student where classes like concat(#{content},'%') limit #{start},#{size}")
    List<Student> search_by_class(@Param("start") int start, @Param("size") int size,@Param("content") String content);


    int pay(String id, Double money);

    @Select("select img_src from student")
    public List<Student> find_imgsrc();








}


