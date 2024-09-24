package com.example.sc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.sc.entity.Bill;
import com.example.sc.entity.Food;
import com.example.sc.entity.Goods;
import com.example.sc.entity.ShopMoney;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoodMapper extends BaseMapper<Food> {

    //获取菜单
    @Select("select * from food where type=#{type}")
    List<Goods> menu(String type);

    //获取所有
    @Select("select * from food")
    List<Goods> all();



    //获取订单
    @Select("select * from food_bill")
    List<Bill> bill();

    //添加订单
    @Insert("insert into food_bill (money,content,date) values(#{money},#{content},#{date})")
    int add_bill(@Param("money") Double money, @Param("content") String content,@Param("date") String date);

    //更改已支付
    @Update("update food_bill set state='已支付',stu_name = #{name} where id = #{id}")
    void payed(@Param("id") String id,@Param("name") String name);

    //增加菜品
    @Insert("insert into food (money,name,img_src) values (#{food.money},#{food.name},#{food.img_src})")
    int add(@Param("food") Food food);

    //更改状态
    @Update("update food_bill set state=#{state} where id = #{id}")
    int change(@Param("id") String id,@Param("state") String state);

    //查询菜单
    List<Goods> search(Goods goods);

    //删除菜单
    int del(int[] id);

    //查询第n天数据
    @Select("SELECT * FROM(SELECT * FROM shop_money ORDER BY DATE DESC LIMIT 0,#{day})aa ORDER BY DATE")
    List<ShopMoney> rank(int day);

    @Insert("insert into shop_money(money,date) values(#{money},curdate()) on duplicate key update money = money + #{money}")
    void bill_money(Double money);


    List<Bill> get_bill(@Param("state") String state);

    void multi_del(List<String> list);

    @Select("select * from food where type = #{type}")
    List<Food> find_list(String type);

    @Select("select * from food")
    List<Food> find_imgsrc();

    @Select("select @@identity")
    int get_id();


    @Select("select * from shop_money where date like concat(#{date},'%')")
    List<ShopMoney> new_rank(String date);


}
