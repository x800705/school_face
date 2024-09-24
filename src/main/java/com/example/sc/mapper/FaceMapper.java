package com.example.sc.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.example.sc.entity.face;

@Mapper
public interface FaceMapper {
    face is_use(int id);
    Boolean pay(face f);
}