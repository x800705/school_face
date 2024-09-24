package com.example.sc.controller;

import com.example.sc.entity.Admin;
import com.example.sc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin
public class TestController {

    @Autowired
    AdminService adminService;
    private String paths = "D:/JavaWeb/sc/src/main/resources/static/";

    @GetMapping("/hello")
    public String hello() {
        return "Hello,World";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Admin test(Admin admin) {
        return admin;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Admin login(Admin admin) {
        return adminService.logins(admin);

    }

    @PostMapping("/img")
    public String pic(MultipartFile file,String type,HttpServletRequest request) {
        System.out.println(file.getOriginalFilename());
        //String paths = request.getServletContext().getRealPath("/upload/");
        //System.out.println(paths);
        String random = UUID.randomUUID().toString();
        System.out.println(type);
        String path = paths + type + "/" + random + ".jpg";

        saveFile(file, path);
        return random;
    }


    public void saveFile(MultipartFile photo, String path) {


        File file = new File(path);
        System.out.println(path);
        try {
            photo.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(paths + name));

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }








}
