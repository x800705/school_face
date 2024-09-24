package com.example.sc.controller;


import com.example.sc.entity.Admin;
import com.example.sc.entity.Student;
import com.example.sc.service.AdminService;
import com.example.sc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller

public class AdminController {

    @Autowired
    public AdminService adminService;

    // 管理员登录界面
    @RequestMapping("/admin_login")
    public String sign() {
        return "admin_login.html";
    }

    // 登录处理
    @RequestMapping(value = "/LoginIn",method = RequestMethod.POST)
    public String login(String id,String pwd,HttpSession session,Map<String,Object> map) {
        // 已登录则直接跳转
        if (session.getAttribute("names") != null) {
            map.put("name",session.getAttribute("names"));
            return "admin_index.html";
        } else {

            System.out.println(id);
            System.out.println(pwd);
            Admin admin = adminService.login(id, pwd);

            //Student stu = studentService.login(id,pwd);
            //Student s_name = studentService.get_name(id);

            if (admin != null) {
                Admin a_name = adminService.find_name(id);
                // session保存用户信息
                session.setAttribute("names",a_name);
                map.put("name",a_name);

                return "admin_index.html";
            } else {
                return "error.html";
            }
        }
    }


    @RequestMapping("/find_all_admin")
    public String find_all_admin(Map<String,Object> map){
        List<Admin> all_admin = adminService.find_all_admin();
        map.put("admins",all_admin);

        return "find_admin.html";
    }

    @RequestMapping("/delete_admin/{id}")
    public String delete_admin(@PathVariable String id,Map<String,Object> map){
        adminService.delete_admin(id);
        List<Admin> all_admin = adminService.find_all_admin();
        map.put("admins",all_admin);

        return "redirect:/find_all_admin";

    }


    @RequestMapping("/RemoveLogin")
    public String remove_login(HttpSession session){
        session.removeAttribute("names");
        return "redirect:/admin_login";
    }

    @RequestMapping("/ToAddadmin")
    public String to_add_admin(){
        return "add_admin.html";
    }

    @RequestMapping("/add_admin")
    public String add_admin(String id,String name,String pwd){
        adminService.add_admin(id,name,pwd);
        return "redirect:/find_all_admin";
    }

    @RequestMapping("/to_change_admin/{id}")
    public String to_change_admin(@PathVariable String id,Map<String,Object> map){
        map.put("change",adminService.find_name(id));
        return "change_admin.html";
    }

    @RequestMapping("/change_admin")
    public String change_admin(String id,String name,String pwd){
        adminService.change_admin(id,name,pwd);
        return "redirect:/find_all_admin";
    }

    @RequestMapping("student/find_all")
    public String find_all_student(){
        return "all_student.html";
    }





}
