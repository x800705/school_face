<template>
  <div>



    <div style="float:left">
      <table>
        <tr v-for="i in list">
          <td><img :src="'http://localhost:8088/download?name=' + i.imgSrc"></td>
          <td><div>{{i.content}}</div> <div style="font-size: 40px;color: red;font-weight: bold">￥{{i.money}}</div></td>

          <td><el-button @click="add(i.content,i.money,i.imgSrc)">购买</el-button></td>
        </tr>
      </table>

    </div>



    <div style="float:right">
      <table>
        <tr>
          <th>品名</th>
          <th>单价</th>
          <th>数量</th>
          <th>总价</th>
        </tr>
        <tr v-for="(i,index) in new_list">
          <td><img :src="'http://localhost:8088/download?name=' + i.imgSrc"></td>
          <td>{{i.name}}</td>
          <td>{{i.money}}</td>


          <td><el-input-number v-model="i.num"  :min="1" :max="10" @change="handleChange"></el-input-number></td>
          <td>{{i.num * i.money}} </td>
          <td><el-button type="danger" @click="del(index)">删除</el-button></td>
        </tr>

      </table>
      总计：{{total}} <el-button @click="pay">提交</el-button>

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
      num: 0,
      msg: "",
      content: ""
    }

  },

  created() {

    axios.get('food/menu').then((res) => {
      console.log(res.data);
      this.list = res.data


    })

  },

  methods: {
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

      //解析订单详情json数组
      console.log(123132)
      this.content = "";
      for (var i in this.new_list) {
        this.content = this.content + this.new_list[i]['name'];
        this.content = this.content + '*';
        this.content = this.content + this.new_list[i]['num'];
        this.content = this.content + ' ';
      }


      let param = new URLSearchParams();
      //param.append("content",this.new_list)
      param.append("id", this.num);
      param.append("money", this.total);
    }
  }
}
</script>

<style scoped>
img{
  width: 300px;
  height:150px;
}
</style>