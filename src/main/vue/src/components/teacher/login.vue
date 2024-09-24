<template>
  <div class="body">

    <div class="box">
      <div class="login">
        <el-form>
          <h3 style="text-align: center">教师系统</h3>

          <!--导航栏 -->
          <el-menu  default-active="id" class="el-menu-demo" mode="horizontal" style="background-color:transparent">
            <el-menu-item @click="methond = 'id'" index="id">账号密码登录</el-menu-item>
            <el-menu-item @click="methond = 'phone'" index="phone">手机验证码登录</el-menu-item>
          </el-menu>

          <div v-if="methond === 'id'">

            <div> <el-input class="input"  placeholder="请输入账户" v-model="user.id"></el-input> </div>
            <div> <el-input class="input" placeholder="请输入密码" type="password" v-model="user.pwd"></el-input> </div>


            <el-button type="primary" class="button" @click="id_login()" :loading="loading">提交</el-button>
          </div>

          <div v-if="methond === 'phone'">
            <div> <el-input class="input"  placeholder="请输入手机号" v-model="user.phone"> </el-input> </div>
            <div> <el-input class="input" placeholder="请输入验证码" v-model="user.code"></el-input> </div>

            <el-button @click="get_core()">获取验证码</el-button>
            <el-button type="primary" class="button" @click="phone_login()">提交</el-button>
          </div>





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
  background-image: linear-gradient(to right,lightcyan,skyblue);
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
  height: 500px;
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

      },
      methond:"id",
    }
  },

  created() {
    if(sessionStorage.getItem("teacher") != null){
      this.$message.success("你已经登录了")
      this.$router.push("index")
    }
  },




  methods:{

    //用户名密码登录
    id_login(){
      this.loading = true;
      axios.post("teacher/id_login",this.user).then((responds) =>{
        console.log(responds.data);
        this.loading = false;
        //登录成功
        if(responds.data !== ""){
          //储存登录信息
          sessionStorage.setItem("teacher",JSON.stringify(responds.data));
          this.$message.success("欢迎回来,"+responds.data.name)


          this.$router.push('index')




        }
        else{
          this.$message.error("账号或密码输入错误")
        }


      }).catch(res =>{
        this.loading = false;
        this.$message.error("服务器出问题了,请稍后再试！")
      })
    },

    check(){
      this.loading = true;
      axios.post("admin/new_login",this.user).then((responds) =>{
        console.log(responds.data);
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


    phone_login(){
      axios.get("teacher/phone_login?phone="+this.user.phone + "&code=" + this.user.code).then(res=>{

        if(res.data === "no_phone") this.$message.error("本号码未登记");
        else if(res.data === "error_code") this.$message.error("验证码输入错误");
        else{
          console.log(res.data)
          sessionStorage.setItem("teacher",JSON.stringify(res.data)); //设置session
          this.$message.success("欢迎回来" + res.data.name)
          this.$router.push("index")
        }





      })
    },

    get_core(){
      axios.get("teacher/get_code?phone=" + this.user.phone).then(res=>{
        this.$message.success("验证码已发送")
        console.log(res);

      })
    },

  }

}
</script>
