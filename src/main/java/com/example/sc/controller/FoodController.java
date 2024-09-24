package com.example.sc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.sc.entity.Bill;
import com.example.sc.entity.Food;
import com.example.sc.entity.Goods;
import com.example.sc.entity.ShopMoney;
import com.example.sc.mapper.FoodMapper;
import com.example.sc.service.FoodService;
import com.example.sc.service.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@CrossOrigin
@RequestMapping("food")
public class FoodController{

    @Autowired
    FoodService foodService;



    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    FoodMapper foodMapper;

    //查询菜单
    @GetMapping("/menu")
    public Object menu(String type){

        //缓存
        if(redisTemplate.opsForValue().get(type) != null){
            return redisTemplate.opsForValue().get(type);
        }
        //数据库
        else{
            Object object = foodService.menu(type);
            redisTemplate.opsForValue().set(type,object,1800, TimeUnit.SECONDS);
            return foodService.menu(type);
        }



    }

    //按照条件查找菜单
    @GetMapping("/find_menu")
    public List<Goods> find_menu(Goods goods){
        return foodMapper.search(goods);
    }


    //删除
    @PostMapping("/del/{id}")
    public int del(@PathVariable int id){
        return foodMapper.deleteById(id);
    }



    //查询订单
    @GetMapping("/bill")
    public List<Bill> bill(){return foodService.bill();}

    //添加订单
    @PostMapping("/add_bill")
    public int add_bill(Double money,String content,String date){

        foodService.add_bill(money,content,date);
        return foodService.get_id();

    }

    //发送id和money给支付端
    @PostMapping("/pay")
    public void pay(int id,Double money){
        //发送WebSocket请求
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("money",money);
        WebSocket.sendMessage(String.valueOf(jsonObject));

    }
    //标记已支付和读取支付学生
    @PostMapping("/payed")
    public void payed(String id,String name){
        foodService.payed(id,name);
    }

    //增加菜品
    @PostMapping("/add")
    public int add(Food food){return foodService.add(food);}

    //更改状态
    @PostMapping("/change")
    public int change(String id,String state,Double money){
        return foodService.change(id,state,money);
    }



    //查询第n天的营业额
    @GetMapping("/money")
    public List<ShopMoney> rank(int day){return foodMapper.rank(day);}

    //查询第n天的营业额
    @GetMapping("/new_money")
    public List<ShopMoney> new_rank(String date){return foodMapper.new_rank(date);}





    @GetMapping("/test")
    public Page<Food> test(int a,int b){
        Page<Food> page = Page.of(a,b);
        return foodMapper.selectPage(page,null);


    }

    //分页查询
    @GetMapping("/menu_page")
    public Object menu_page(String name,int page,int size){

            // 分页查询参数
            Page<Food> pages = Page.of(page,size);

            // 导入条件
            LambdaQueryWrapper<Food> queryWrapper = new LambdaQueryWrapper<Food>();
            queryWrapper.likeRight(Food::getName,name);

            //进行查询
            Object object = foodMapper.selectPage(pages,queryWrapper);
            return object;

    }

    //菜品修改
    @PostMapping("/new_change")
    public int new_change(@RequestBody Food food){
        redisTemplate.delete("menu");
        redisTemplate.delete(food.getType());
        return foodMapper.updateById(food);
    }


    //MBP新增
    @PostMapping("/new_add")
    public int new_add(@RequestBody Food food){
        //删除缓存
        redisTemplate.delete("menu");
        redisTemplate.delete(food.getType());
        return foodMapper.insert(food);
    }

    //MBP删除
    @GetMapping("/new_del")
    public void new_del(String id){
        String type = foodMapper.selectById(id).getType();
        redisTemplate.delete(type);
        foodMapper.deleteById(id);
    }



    //MBP查询
    @GetMapping("/new_get")
    public Food new_get(String id){
        return foodMapper.selectById(id);
    }

    //MBP条件查询
    @GetMapping("/newer_get")
    public List<Food> newer_get(Food food){
       LambdaQueryWrapper<Food> lqw = new LambdaQueryWrapper<Food>();

       lqw.eq(food.getName() != null,Food::getName,food.getName());
       lqw.eq(food.getMoney() != null,Food::getMoney,food.getMoney());


       return foodMapper.selectList(lqw);

    }


    //MBP上下限查询
    @GetMapping("/ud_get")
    public List<Food> ud_get(int up,int down){
        LambdaQueryWrapper<Food> lqw = new LambdaQueryWrapper<Food>();

        lqw.gt(Food::getMoney,up);
        lqw.lt(Food::getMoney,down);

        return foodMapper.selectList(lqw);
    }

    //查询投影
    @GetMapping("/new_map")
    public List<Map<String, Object>> new_map(){
        QueryWrapper<Food> lqw = new QueryWrapper<>();
        lqw.select("count(*) as count,money");
        lqw.groupBy("money");
        List<Map<String, Object>> maps = foodMapper.selectMaps(lqw);
        return maps;


    }


    //登录查询
    @GetMapping("/new_login")
    public Food new_login(String name, String money){
        LambdaQueryWrapper<Food> lqw = new LambdaQueryWrapper();
        lqw.eq(Food::getName,name).eq(Food::getMoney,money);

        Food food = foodMapper.selectOne(lqw);

        return food;

    }

    //批量删除
    @PostMapping("/dels")
    public int dels(@RequestBody ArrayList<String> ids){
        System.out.println(ids);
        return foodMapper.deleteBatchIds(ids);





    }

    //清理缓存
    @GetMapping("/clean")
    public void clean(){


        List<Food> list = foodMapper.find_imgsrc();
        List<String> new_list = new ArrayList<>();
        for(int i = 0 ;i<list.size();i++){
            new_list.add(list.get(i).getImgSrc());

        }

        System.out.println(new_list);


        File file = new File("D:\\JavaWeb\\sc\\src\\main\\resources\\static\\food");
        File[] files = file.listFiles();
        System.out.println(files);

        for(File file2 : files){
            System.out.println(file2.getName());
            String name = "food/" + file2.getName();
            System.out.println(name);
            System.out.println(new_list.contains(name));
            if(!new_list.contains(name)){
                file2.delete();
            }

        }





        System.out.println(new_list);

    }

    @PostMapping("bill_money")
    public void bill_money(Double money){
        foodMapper.bill_money(money);
    }





















}
