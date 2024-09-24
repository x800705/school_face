<template>
    <div>

      <!--导航栏 -->
      <el-menu default-active="menu" class="el-menu-demo" mode="horizontal" @select="handleSelect">
        <el-menu-item index="menu">全部菜品</el-menu-item>
        <template v-for="i in type">
          <el-menu-item :index="i.type">{{i.type}}</el-menu-item>

        </template>
      </el-menu>

      <!--el-table的菜品菜单-->
      <el-table
          :data="list"
          style="width: 50%;color:black;font-size:20px;float:left">
        <el-table-column
            prop="imgSrc"
            label="图片"
            width="180">
          <template slot-scope="scope">
            <img :src="'http://localhost:8088/download?name=' + scope.row.imgSrc">
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="菜名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="money"
            label="价格"
            width="100">
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">

            <el-button @click="add(scope.row.name,scope.row.money,scope.row.imgSrc)">购买</el-button>

          </template>
        </el-table-column>



      </el-table>


      <!--v-for的菜品菜单-->
      <!--
    <div style="float:left">
        <table>
            <tr v-for="i in list">
                <td><img :src="'http://localhost:8088/download?name=' + i.imgSrc"></td>
                <td><div>{{i.content}}</div> <div style="font-size: 40px;color: red;font-weight: bold">￥{{i.money}}</div></td>


            </tr>
        </table>

    </div>
        -->





      <div style="float:right;width:50%">
        <table>
          <tr>
            <th>图片</th>
            <th>品名</th>
            <th>单价</th>
            <th>数量</th>
            <th>总价</th>
          </tr>
          <tr v-if="new_list.length === 0">暂无数据</tr>
          <tr v-else v-for="(i,index) in new_list">
            <td><img :src="'http://localhost:8088/download?name=' + i.imgSrc"></td>
            <td>{{i.name}}</td>
            <td>{{i.money}}</td>


            <td><el-input-number v-model="i.num"  :min="1" :max="10" @change="handleChange"></el-input-number></td>
            <td>{{i.num * i.money}} </td>
            <td><el-button type="danger" @click="del(index)">删除</el-button></td>
          </tr>

        </table>
        <div style="float:right">总计：{{total}} <el-button @click="pay">提交</el-button> </div>

        <div>{{msg}}</div>
      </div>






    </div>
</template>


<script>

import axios from 'axios';

export default {

  data() {
    return {
      list: [],
      new_list: [],
      total: 0,
      num: 100,
      msg: "",
      content: "",
      type:[],
      food_type:"menu",

    }

  },

  created() {
    this.initWebSocket();
    this.req();

  },

  methods: {

    req(){
      axios.get("type/get_all").then((res)=>{
        this.type = res.data
      })
      axios.get('food/menu?type=' + this.food_type).then((res) => {
        console.log(res.data);
        this.list = res.data


      })

    },
    initWebSocket(){//初始化weosocket(必须)
      const wsuri = "ws://localhost:8088/websocket";    //请根据实际项目需要进行修改
      this.websock = new WebSocket(wsuri);      //新建一个webstock对象
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
      console.log(this.websock);
    },

    websocketonmessage(e){ //数据接收
      var data = JSON.parse(e.data)
      if(data.msg === "支付成功") {
        this.msg = "用户已支付成功"
        this.new_list = []
      }


    },
    //时间归一化
    stand(num){
      if(num < 10){
        num = "0" + num;
      }

      return num;
    },

    get_time(){
      var date = new Date();
      var year = this.stand(date.getFullYear());
      var mouth = this.stand(date.getMonth() + 1);
      var day = this.stand(date.getDate());
      var hour = this.stand(date.getHours());
      var min = this.stand(date.getMinutes());
      var sec = this.stand(date.getSeconds());


      var time = year + "-" + mouth + "-" + day + " " +  hour + ":" + min + ":" + sec;
      return time;
    },
    //计算总价
    totals() {
      this.total = 0
      this.new_list.forEach(i => {
        this.total = this.total + i.num * i.money;
      })
    },
    add(name, money, img) {
      this.new_list.push({name: name, money: money, num: 1, imgSrc: img})
      this.totals()
    },
    handleChange() {
      this.totals()
    },
    del(id) {
      this.new_list.splice(id, 1)
      this.totals()
    },
    pay() {
      //1、向商家端写入订单,标记待支付

      //订单id,订单内容,订单金额,订单状态,订单时间

      //订单内容 content
      //解析订单详情json数组

      this.content = "";
      for (var i in this.new_list) {
        this.content = this.content + this.new_list[i]['name'];
        this.content = this.content + '*';
        this.content = this.content + this.new_list[i]['num'];
        this.content = this.content + ' ';
      }


      this.num = this.num + 1

      let param = new URLSearchParams();
      //param.append("content",this.new_list)

      param.append("money", this.total);
      param.append("content", this.content);
      //param.append("state", "待支付");
      param.append("date", this.get_time());

      axios.post("food/add_bill",param).then(res =>{
        console.log(res)
        this.num = res.data
        this.msg = "等待用户支付"
      })





      //2、发送WebStock请求
      param = new URLSearchParams();
      param.append("id", this.num);
      param.append("money", this.total);
      axios.post("pay/pay",param).then(res =>{
        console.log(res)
      })


      //3、将食品销量加入表中
      //订单号,食品号,数量,销售时间
    },

    handleSelect(e){
      this.food_type = e;
      console.log(this.food_type)
      this.req();
    }
  }
}
</script>

<style scoped>
img{
  height:80px;
  width: 120px;
}
th,td{
  width: 100px;
  text-align: center;
}
</style>
