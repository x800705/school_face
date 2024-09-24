package com.example.sc;

import com.example.sc.config.TextConfig;
import com.example.sc.entity.Goods;
import com.example.sc.mapper.FoodMapper;
import com.example.sc.mapper.TestMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class new_test {



    @Test
    public void get_core() {
         String[] array = {"02b87359-4319-4374-bdc1-a55e35f40676.jpg"};

         List<String> list = new ArrayList<>();

         list.add("1");
         list.add("2");


        System.out.println(list.contains("3"));



    }


}
