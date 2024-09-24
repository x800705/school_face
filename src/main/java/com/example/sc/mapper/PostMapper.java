package com.example.sc.mapper;

import com.example.sc.entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.ListResourceBundle;

@Mapper
public interface PostMapper {

    @Insert("INSERT INTO post (post_id,content,time) VALUES (#{post_id},#{content},#{time}) ")
    void post(@Param("post_id") String post_id,@Param("content") String content,@Param("time") String time);

    @Insert("INSERT INTO post_content (post_id,log_id,content,time) VALUES (#{post_id},#{log_id},#{content},#{time}) ")
    void post_log(@Param("log_id") int log_id,@Param("content") String content,@Param("post_id") String post_id,
                  @Param("time") String time);


    @Select("select * from post Order By time Desc")
    @Results({

            @Result(column = "id",property = "id"),
            @Result(column = "log_id",property = "log_id"),
            @Result(column = "post_id",property = "post_id"),
            @Result(column = "content",property = "content"),
            @Result(column = "post_id",property = "students",javaType = List.class,
                    many=@Many(select = "com.example.sc.mapper.StudentMapper.get_student"))
    })
    List<Post> find();

    @Select("select * from post Order By time Desc")
    @Results({

            @Result(column = "id",property = "id"),
            @Result(column = "log_id",property = "log_id"),
            @Result(column = "post_id",property = "post_id"),
            @Result(column = "content",property = "content"),
            @Result(column = "count",property = "count",
                    one=@One(select = "com.example.sc.mapper.PostMapper.counts"))
    })
    List<Post> findes();

    @Select("select count(*) from post_content where log_id = #{id}")
    int counts(@Param("id") int id);



    @Select("select * from post where content like concat('%',#{title},'%') Order By time Desc")
    @Results({

            @Result(column = "id",property = "id"),
            @Result(column = "log_id",property = "log_id"),
            @Result(column = "post_id",property = "post_id"),
            @Result(column = "content",property = "content"),
            @Result(column = "post_id",property = "students",javaType = List.class,
                    many=@Many(select = "com.example.sc.mapper.StudentMapper.get_student"))
    })

    List<Post> like_find(@Param("title") String title);



    @Select("select * from post where id = #{id}")
    List<Post> find_one(@Param("id") int id);

    @Select("select * from post_content where log_id = #{log_id} Order By time Asc")

    @Results({

            @Result(column = "id",property = "id"),
            @Result(column = "log_id",property = "log_id"),
            @Result(column = "post_id",property = "post_id"),
            @Result(column = "content",property = "content"),
            @Result(column = "post_id",property = "students",javaType = List.class,
            many=@Many(select = "com.example.sc.mapper.StudentMapper.get_student"))
    })
    List<Post> get_log(@Param("log_id") int log_id);

    @Select("select * from post_content where log_id = #{log_id} Order By time Asc")
    List<Post> get_logs(@Param("log_id") int log_id);


    @Update("update post set time = #{time} where id=#{id}")
    void up_time(@Param("id") int id,@Param("time") String time);

    @Update("update post set count = count + 1 where id = #{id}")
    void count(@Param("id") int id);


    @Select("select * from post Order By time Desc")
    List<Post> finds();


}
