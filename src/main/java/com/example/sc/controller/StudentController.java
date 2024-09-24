package com.example.sc.controller;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.CheckIn;
import com.example.sc.entity.Student;
import com.example.sc.entity.consume;
import com.example.sc.mapper.CheckInMapper;
import com.example.sc.mapper.StudentMapper;
import com.example.sc.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class StudentController {
    @Autowired
    public StudentService studentService;

    @Autowired
    public StudentMapper studentMapper;

    @Autowired
    public CheckInMapper checkInMapper;

    @PostMapping("/DelOne")
    public int DelOne(@RequestBody Student student){
        System.out.println(student);
        System.out.println(student.getId());
        String paths = "D:/JavaWeb/sc/src/main/resources/static/";
        File file = new File(paths + student.getImgSrc());
        if(file.delete()) System.out.println("删除成功");


        return studentService.Delone(student.getId());
    }

    @GetMapping("/findOne/{id}")
    public Student getOne(@PathVariable String id){
        return studentService.findOne(id);
    }

    @GetMapping("find_one/{id}")
    public Student find_one(@PathVariable String id){

        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getId,id);

        return studentMapper.selectOne(queryWrapper);

    }

    @GetMapping("/findAll")
    public List<Student> find_all(){return studentService.findAll();}

    @GetMapping("/findStu")
    public List<Student> find_student(int num,int page){ //
        int size = num;
        int start = (page-1)*num;


        return studentService.find_student(start,size);
    }

    @GetMapping("/search")
    public List<Student> search(int num,int page,String content,String methon){ //
        int size = num;
        int start = (page-1)*num;


        return studentService.search(start,size,content,methon);
    }


    @GetMapping("/find_list")
    public List<consume> find_student_comsume(String id,int num,int page){
        int size = num;
        int start = (page-1)*num;
        return studentService.find_student_consume(id,start,size);
    }

    @GetMapping("/find_lists")
    public List<consume> find_students_consume(String id,String date,int page){
        // select SELECT * FROM consume WHERE  DATE LIKE "2022-12%" and id = #{id} limit page,10
        page = page * 10;
        return studentService.find_students_consume(id,date,page);


    }

    @PostMapping("/login")
    public List<Student> find(String id,String pwd){
        System.out.println(id);
        System.out.println(pwd);
        return studentService.login(id,pwd);
    }

    @PostMapping("/add_money")
    public int add_money(String id, double money, String date ,String content){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return studentService.add_money(id,money,dates,content);

    }


    @PostMapping("/ban")
    public int ban(String id){
        return studentService.ban(id);
    }


    @PostMapping("/update_pwd")
    public int up_pwd(String id,String pwd){
        return studentService.up_pwd(id,pwd);
    }

    @PostMapping("/change")
    public int change_money(String id,double money,String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dates = null;
        try {
            dates = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return studentService.change_money(id,money,dates);
    }

    @PostMapping("/add")
    public int add_student(String id,String name,String classes,String pwd){
        return studentService.add_student(id,name,classes,pwd);
    }

    @PostMapping("/new_add")
    public int new_add_student(Student student){
        return studentMapper.insert(student);
    }

    @PostMapping("/changes")
    public int change_student(String id,String name,String classes,String pwd) {
        return studentService.change_student(id,name,classes,pwd);
    }

    @PostMapping("/new_changes")
    public int new_change_student(@RequestBody  Student student) {
        return studentMapper.updateById(student);

    }

    @PostMapping("/pay")
    public int pay(String id,Double money){
        return studentService.pay(id,money);
    }


    @GetMapping("ali_pay")
    public String ali_pay() throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do","2021000122609647","MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC6Eyheos0ApIWXsssIDzyiXQJxOFGdiuoSHle4PLl2+Bkureuj1c0wsJZhCJ5l44SH8RIdRHyts40u0uhajFDRO14+VkVR07tEzA5CnAYeGoEW0ByzeqcFqDkMARcw0w/l6WaisdlerFlbscL9b5stFENo871sFBIbLvhalFQ5aLYg0EnH0RzwoPGt/TxOytfCjoN5TCp5Ocu+4qKBkJJRGSkgNWQauI2V2VOww0nSrk8SX4OWii1obuumyGomovSijFB1fNs7MNiMivB+q5tHTs1uAp9TlUwETbxRocwqAv1fhHc1B7rL8/nLW1LzpjKOssOOVo0mU8k03io1q91nAgMBAAECggEAKpXt3FLYJjiBFqnly69pCiPNRkpEa5D6ZzEolFLZg3/ei49kabep4WXY1NZbIiiDEXW71pTxD/QS8M92PBr2+BvlQtENxl918ZXeAaKhx8v7FVSF7RG6HhJVFAk/3H3BmrXjNoMLo55ASTsSzFv5oMSNdPyrsouZZT1Hu7tM12JaQZCwA22sncZQaI/gskgbzVYv3MdywN6R1IMTpC7WF4K5BqBj8lbohwR4kL1599zyNWuaqO6zFK61ZdYB9T+8HPC/DDlQ6Oa4/JwccCfE9khQQmsiN4yyEl4w5aqwVT3G4luvsdLuQs/CwhUy07N7nQ9OXS/gMMRB/JiE2KY/aQKBgQDw0rchxETuGlwtSJcjw0zlW7AeL9Rb7md8SilgZLF5aMC7D3AawFyFMW1/+gDdDsSEoY9c+CRDUCl5bZlEgTDkwhn9tGu2Pw0w0bD7k4k6IWtE9VCvjkHMIQ9JCIZvcuxaNx+Pzk8dpFtG33/0YSziQfLk6VyfqIfPErl0phMYLQKBgQDFzStFvnZi+LIQ9uFAs+URslNf/x2Grm+/ugDjo1cugm/Nvd1+97FogJmk5LAvMifZHNwY0ZzJhd4DkEHW/lKmrSSl25noEPOCQqiCrdt6UncXkpAqZ3Rpqg/z/w6G9Y4B5hwxtK0b1hjrax192/ILw2f/VoLxDC+SajHt4b4UYwKBgB+AoP4ky5OOFTLDRSojeSKjrNU2EoR4Gkdy+Bio82JbZqLRsSt+sFuoDJfqH0TLkr961uMlfrDjgplk0eDZOYkjLv02JcTwWIxjUdCHCzN0fbDGmmhspoTKalJ2cAeSNALVJSbNqSHKhzotMHLKZ3GtiNWTBU/JXwKig2oT83l9AoGAYAXc1uvCPwOqNfATo7xxHKksLnMDm2k9GJ8sZVXbMdnieseMKmIGPswHSnQ2T4MSsyR+eJHxgic5gxNZ0wv9haqSGu5DhdQM0pSXh3DtFjHIXKoq0FsOuWj0e6e7HqtAOA3EbCDg1J2X1VlXpMzMcQd4ig0uXeRjh7mD6Y5oHy8CgYBCqfIxW9yhSQ4eM+pojb/8jBshI0D+Jy7cHDrNSZUdsJo14yFTDwM+Mmi7uS9/dkChT8+ut5Hmujvz0sHUy4Ukurr0zsRHmRigboH2ejSXfRgP3wJgaGBVwWjLchKCaHaxkVFyb6JDfHWEbY5xBuqYWBPpuTe+0B9ugJJpDAXV6g==","json","GBK","MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuhMoXqLNAKSFl7LLCA88ol0CcThRnYrqEh5XuDy5dvgZLq3ro9XNMLCWYQieZeOEh/ESHUR8rbONLtLoWoxQ0TtePlZFUdO7RMwOQpwGHhqBFtAcs3qnBag5DAEXMNMP5elmorHZXqxZW7HC/W+bLRRDaPO9bBQSGy74WpRUOWi2INBJx9Ec8KDxrf08TsrXwo6DeUwqeTnLvuKigZCSURkpIDVkGriNldlTsMNJ0q5PEl+DlootaG7rpshqJqL0ooxQdXzbOzDYjIrwfqubR07NbgKfU5VMBE28UaHMKgL9X4R3NQe6y/P5y1tS86YyjrLDjlaNJlPJNN4qNavdZwIDAQAB","RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
//异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl("");
//同步跳转地址，仅支持http/https
        request.setReturnUrl("");

/******必传参数******/
        JSONObject bizContent = new JSONObject();
//商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", "202108170112355123");
//支付金额，最小值0.01元
        bizContent.put("total_amount", 25);
//订单标题，不可使用特殊符号
        bizContent.put("subject", "love");
//电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");


        request.setBizContent(bizContent.toString());


            AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

            return response.getBody();





    }

    @GetMapping("clean")
    public void clean(){

        System.out.println(studentMapper.find_imgsrc());
        List<Student> list = studentMapper.find_imgsrc();
        List<String> new_list = new ArrayList<>();
        for(int i = 0 ;i<list.size();i++){
            new_list.add(list.get(i).getImgSrc());

        }

        System.out.println(new_list);


        File file = new File("D:\\JavaWeb\\sc\\src\\main\\resources\\static\\student");
        File[] files = file.listFiles();
        System.out.println(files);

        for(File file2 : files){
            System.out.println(file2.getName());
            String name = "student/" + file2.getName();
            System.out.println(name);
            System.out.println(new_list.contains(name));
            if(!new_list.contains(name)){
                file2.delete();
            }

        }





        System.out.println(new_list);

    }


    //分页查询 学生考勤情况
    @GetMapping("/page")
    public Page<Student> menu_page(String state,String classes,int page,int size){

        // 分页查询参数
        Page<Student> pages = Page.of(page,size);

        // 导入条件
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!Objects.equals(state, "null"),Student::getState,state);




        queryWrapper.eq(Student::getClasses,classes);



        //进行查询
        Page<Student> object = studentMapper.selectPage(pages,queryWrapper);
        return object;

    }

    //学生到校
    @PostMapping("/come")
    public int come(@RequestBody Student student){
        //更改学生到校状态
        studentMapper.updateById(student);

        CheckIn checkIn = new CheckIn();

        checkIn.setId(student.getId());
        checkIn.setName(student.getName());
        checkIn.setClasses(student.getClasses());
        Date date = new Date();
        checkIn.setTime(date);
        checkIn.setState(1);


        return checkInMapper.insert(checkIn);

    }

    //学生出校
    @PostMapping("/go")
    public int go(@RequestBody Student student){
        //更改学生到校状态
        studentMapper.updateById(student);

        CheckIn checkIn = new CheckIn();

        checkIn.setId(student.getId());
        checkIn.setName(student.getName());
        checkIn.setClasses(student.getClasses());
        Date date = new Date();
        checkIn.setTime(date);
        checkIn.setState(0);
        return checkInMapper.insert(checkIn);
    }


    //学生状态更改
    @PostMapping("/sick")
    public int sick(@RequestBody Student student){
        return studentMapper.updateById(student);
    }

    @PostMapping("/door")
    public int door(@RequestBody Student student){
        studentMapper.updateById(student);

        CheckIn checkIn = new CheckIn();


        checkIn.setId(student.getId());
        checkIn.setName(student.getName());
        checkIn.setClasses(student.getClasses());
        Date date = new Date();
        checkIn.setTime(date);
        return checkInMapper.insert(checkIn);

    }















}
