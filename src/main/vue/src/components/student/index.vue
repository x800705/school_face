<template>
    <div>


    <h1>校园卡管理系统</h1>
    <el-button type="primary"> <router-link to="find_money">查询余额</router-link> </el-button> <br>
    <el-button type="primary" ><router-link to="find_list">查询消费记录</router-link></el-button> <br>
    <el-button type="primary" ><router-link to="add_money">校园卡充值</router-link></el-button> <br>
    <el-button type="primary" @click="lost_card()">校园卡挂失</el-button> <br>
    <el-button type="primary" @click="login_out()">退出登录</el-button> <br>
    


   
    <h2>{{id}}</h2>

    </div>
</template>

<style>





</style>


<script>

import aaa from './login.vue';
import axios from 'axios'


export default{

data(){
  return{
    id:""
  }
},


created(){
  console.log(sessionStorage.getItem("id"));

  this['id'] = sessionStorage.getItem("id");
},

  methods:{
    find_money(){
      this.$router.push('find_money')
    },
    lost_card(){
      if(confirm("确定要挂失卡片吗?")){
        let param = new URLSearchParams(); 
       param.append("id",this['id']);
       console.log(this['id']);
        axios.post("/student/ban",param).then((response) =>{
          if(response.data == 1){
             alert("挂失成功");
             this.login_out();

          }
        })
      }
    },
    login_out(){
      sessionStorage.removeItem('id');
      this.$router.push('/student/login');

    }
  }
}

</script>
