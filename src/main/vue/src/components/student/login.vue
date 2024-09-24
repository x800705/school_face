<template>
    <div id="App">
    


    <div style="width:30%; padding-left:35%">
    <el-input v-model="id" placeholder="请输入账户" ></el-input>
    <el-input v-model="pwd" placeholder="请输入密码"  show-password></el-input>
    <el-button @click="check()">提交</el-button>
    </div>

    </div>
</template>

<style>

body{

}

.el-input{
  padding-bottom:10px;
}

.el-button{ 
  padding-bottom:10px;
}



</style>

<script>

import axios from 'axios'


export default {
  name: 'App',

  data(){
      return{
        id:"",
        pwd:""
      }
  },

  methods:{

          
       check(){
        
           


       let param = new URLSearchParams(); 
       param.append("id",this['id']);
       param.append("pwd",this['pwd']);
       console.log(this['id']);
       console.log(this['pwd']);
       axios.post("student/login",param).then((responds) =>{
         console.log(responds.data);
         if(responds.data[0].ban == 1){
            alert("登录失败");
            console.log("登录失败");
         }
         else{
         if(responds.data.length == 1){
           console.log("登录成功");
           alert("登录成功");
           sessionStorage.setItem("id",this["id"]);
           this.$router.push('/student/index');
         }
         

         else{
            alert("登录失败");
            console.log("登录失败");
         }
         }
           
         
       })

     
    },
      }
  }

  components: {
     
  }



</script>