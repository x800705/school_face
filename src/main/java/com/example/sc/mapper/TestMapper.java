package com.example.sc.mapper;

import com.example.sc.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper

@Repository
public interface TestMapper {

    List<Admin> findAll();

    Admin find_name(String name);

    List<Admin> find(Admin admin);


}
