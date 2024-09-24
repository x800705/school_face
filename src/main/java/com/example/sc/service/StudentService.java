package com.example.sc.service;

import com.example.sc.entity.Student;
import com.example.sc.entity.consume;
import com.example.sc.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;


    public Student findOne(String id){
        return studentMapper.findOne(id);
    }

    public List<Student> login(String id,String pwd){return studentMapper.login(id,pwd);}

    public Student get_name(String id){return studentMapper.get_name(id);}

    public List<Student> findAll(){return studentMapper.findAll();}

    public List<Student> find_student(int start,int end){return studentMapper.find_student(start,end);}

    public List<consume> find_student_consume(String id,int start,int end){return studentMapper.find_student_consume(id,start,end);}

    public int add_money(String id, double money, Date date,String content){


        if(studentMapper.add_money(id,money) == 1) return studentMapper.add_consume(id,money,date,content);
        else return 0;
    }

    public int ban(String id){return studentMapper.ban(id);}


    public int  Delone(String id){return studentMapper.Delone(id);}


    public int up_pwd(String id,String pwd){return studentMapper.up_pwd(id,pwd);}


    public List<consume> find_students_consume(String id,String date,int page){
        return studentMapper.find_students_consume(id,date,page);

    }

    public int change_money(String id,double money,Date date){
        if(studentMapper.change_money(id,money) == 1) return studentMapper.add_consume(id,0,date,"人为更改");
        else return 0;
    }

    public List<Student> search(int start, int size, String content,String methon) {
        if(methon.equals("id")){
            return studentMapper.search_by_id(start,size,content);
        }
        else if(methon.equals("name")){
            return studentMapper.search_by_name(start,size,content);
        }
        else{
            return studentMapper.search_by_class(start,size,content);
        }

    }

    public int add_student(String id,String name,String classes,String pwd){
        return studentMapper.add_student(id,name,classes,pwd);
    }

    public int change_student(String id,String name,String classes,String pwd){
        return studentMapper.change_student(id,name,classes,pwd);
    }


    public int pay(String id, Double money) {
        return studentMapper.pay(id,money);
    }
}
