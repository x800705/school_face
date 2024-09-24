<template>
      <div class="body">

      <div class="box">
      <div class="login">
        <h3 style="text-align: center">智慧校园管理系统</h3>

          <el-form :rules="rules"  class="demo-ruleForm">


               <el-input v-model="user.id" placeholder="请输入账户"></el-input>

                <el-input v-model="user.pwd" placeholder="请输入密码"  show-password></el-input>
                <el-button @click="check()" class="a" type="primary" :loading="loading">提交</el-button>
          </el-form>




    </div>

      </div>

      </div>
</template>

<style scoped>


.body{
  width: 100%;
  height: 100vh;
  display: flex;
  background-image: linear-gradient(to right,skyblue,lightgreen);
}
.login{
  border-radius: 25px;
  margin: 0px auto;
  width: 300px;


}

.box{
  border-radius: 25px;
  background-color: rgba(255,255,255,0.5);
  margin: 180px auto;
  width:450px;
  height: 350px;
}

.input{

}



.a{
    margin:auto
}

.el-button{
  width: 100%;
  margin-top:20px;

}

.el-input{
  width: 100%;
  margin-top:20px;

}

.el-form-item{
  margin:0px auto
}




</style>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            user:{},
            id:"",
            pwd:"",
            phone:"",
            code:"",
            login:"id",
            loading:false,
            rules:{

            }
        }
    },

    created() {
      if(sessionStorage.getItem("res") != null){
        this.$router.push("index")
      }
    },




  methods:{


       //登录
       check(){
         this.loading = true;
       axios.post("admin/new_login",this.user).then((responds) =>{

         this.loading = false;
         //登录成功
         if(responds.data !== ""){
             //储存登录信息
             sessionStorage.setItem("admin",JSON.stringify(responds.data));
             this.$message.success("欢迎回来,"+responds.data.name)


                 this.$router.push('front')

         }
         else{
           this.$message.error("账号或密码输入错误")
         }


       }).catch(res =>{
         this.loading = false;
         this.$message.error("服务器出问题了,请稍后再试！")
       })

       },

       new_check(){
         axios.post("admin/new_login",this.user).then(res=>{
           console.log(res);
         })
       },


     get_core(){
         axios.get("admin/get_core" + "?phone=" + this.phone).then(res=>{
           alert("验证码已发送");
           console.log(res);

         })
     },

     check_phone(){
         axios.get("admin/phone_login?phone=" + this.phone + "&code=" + this.code).then(res=>{
           console.log(res);
           if(res.data === "success"){
             alert("登录成功");
             this.$router.push('card')
             sessionStorage.setItem('navkeep','card');
           }
         })
     }
   }

}
</script>
