<template>
<div id="building">
	<div class="login">
    <div class="main">
       <el-form>
     <h3>商家管理系统</h3>

         <!--导航栏
         <el-menu  default-active="id" class="el-menu-demo" mode="horizontal" style="background-color:transparent">
           <el-menu-item @click="methond = 'id'" index="id">账号密码登录</el-menu-item>
           <el-menu-item @click="methond = 'phone'" index="phone">手机验证码登录</el-menu-item>
         </el-menu>
         -->


   <div v-if="methond === 'id'">
	 <div> <el-input class="input"  placeholder="请输入账户" v-model="user.id"></el-input> </div>
	 <div> <el-input class="input" placeholder="请输入密码" type="password" v-model="user.pwd"></el-input> </div>


         <el-button type="primary" class="button" @click="id_login()">提交</el-button>
   </div>

   <div v-if="methond === 'phone'">
     <div> <el-input class="input"  placeholder="请输入手机号" v-model="user.phone"> </el-input> </div>
     <div> <el-input class="input" placeholder="请输入验证码" v-model="user.code"></el-input> </div>

     <el-button @click="getcode()">获取验证码</el-button>
     <el-button type="primary" class="button" @click="phone_login()">注册/登录</el-button>

   </div>





       </el-form>

     </div>

	 </div>

</div>
</template>

<script>
import axios from "axios"


export default {

  name: "new_login",


  data(){
	  return{
		  user:{
			  id:"",
			  pwd:"",
        phone:"",
        code:"",
		  },



		  label_display : "noshow",
      methond:"id"
	  }
  },

  created() {

    if(sessionStorage.getItem("business") != null){
      alert("你已经登录了,欢迎回来")
      this.$router.push("admin")
    }
  },

  methods:{


	  yes(){
		  console.log("id")
		  this.label_display = "show"
	  },

	  no(){
		  console.log("pwd")
	  },

    getcode(){
      axios.post("business/get_code?phone=" + this.user.phone).then(res=>{
        if(res.data === "no_register") alert("该手机号未注册")
        else if(res.data === "success") alert("验证码已发送")

      })

    },

    id_login(){
      axios.get("business/id_login",{params:{
        id:this.user.id,
        pwd:this.user.pwd,
      }}).then(res=>{
          if(res.data !== ""){
            console.log(res.data)
            sessionStorage.setItem("business",JSON.stringify(res.data)); //设置session
            this.$message.success("欢迎回来" + res.data.name)
            this.$router.push("index")

          }
          else{
             this.$message.error("用户名或密码输入错误")
          }
      })
    },

    phone_login(){
      axios.post("business/phone_login?phone="+this.user.phone + "&code=" + this.user.code).then(res=>{
        if(res.data !== ""){
          console.log(res.data)
          sessionStorage.setItem("business",JSON.stringify(res.data)); //设置session
          alert("欢迎回来" + res.data.name)
          this.$router.push("index")
        }
        else{
          alert("验证码输入错误")
        }

      })
    }

  }



}



</script>

<style scoped>
h3{
  text-align: center;

}

.login{
	margin: 180px auto;
	width: 350px;
}

.login-container{
	background-color: aqua;
}

#building{
	  background-color: aliceblue;
	  width:100%;
	  height:100%;
	  position:fixed;
	  background-size:100% 100%;
}

.input{
	line-height: 20px;
	margin-top:10px;


}



.button{
	margin-top:20px;
	width:100%
}


.show{
	position:relative;
	display: block;
}


.noshow{
	position:relative;
	display: none;
}


</style>
