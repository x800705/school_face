package com.example.sc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.sc.entity.Admin;
import com.example.sc.entity.Student;
import com.example.sc.entity.sm;
import com.example.sc.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService{


    @Autowired
    AdminMapper adminMapper;

    public Admin login(String id, String pwd) {
        return adminMapper.login(id, pwd);
    }


    public Admin find_name(String id) {
        return adminMapper.find_name(id);
    }


    public List<Admin> find_all_admin() {
        return adminMapper.find_all_admin();
    }

    public void delete_admin(String id) {
        adminMapper.delete_admin(id);
    }

    public void add_admin(String id, String name, String pwd) {
        adminMapper.add_admin(id, name, pwd);
    }

    public void change_admin(String id, String name, String pwd) {
        adminMapper.change_admin(id, name, pwd);
    }

    public Admin logins(Admin admin) {
        return adminMapper.logins(admin);
    }


    public List<Admin> search_stu(int start, int size, String content, String methon) {
        if (methon.equals("id")) {
            return adminMapper.search_by_id(start, size, content);
        } else {
            return adminMapper.search_by_name(start, size, content);
        }
    }

    public int count() {
        return adminMapper.count();
    }

    public int del(String id) {
        return adminMapper.del(id);
    }

    public int change(String id, String name, String pro, String pwd) {
        return adminMapper.change(id,name,pro,pwd);
    }

    public int add(String id, String name, String pro, String pwd) {
        return adminMapper.add(id,name,pro,pwd);
    }

    public List<Admin> is_phone(String phone) {
        return adminMapper.is_phone(phone);
    }

    public Admin new_login(Admin admin) {
        return adminMapper.new_login(admin);
    }

    public int change_pwd(Admin admin) {
        return adminMapper.change_pwd(admin);
    }
}
