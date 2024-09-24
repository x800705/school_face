<template>
    <div class="aaaa">
      <!--
        <div class="label">
            <div class="label-left">智慧校园管理系统</div>
            <div class="label-right">
              <el-dropdown>
  <span class="el-dropdown-link">
    {{admin.name}}<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="$router.push('me')">个人信息</el-dropdown-item>
                  <el-dropdown-item @click.native="loginout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>

            </div>

        </div>
        -->



  <div class="left">
  <el-col :span="24">
    <el-menu
      class="el-menu-vertical-demo"
      router
      style=""
      background-color="rgb(239, 247, 128)"
      :default-active="default_active"
      :collapse="isCollapse"


      >

      <el-menu-item index="front">
        <i class="el-icon-location"></i>
        <span slot="title">首页</span>
      </el-menu-item>

      <el-menu-item index="student"  v-if="this.admin.pro === '超级管理员'">
        <i class="el-icon-user-solid"></i>
        <span slot="title">学生管理</span>
      </el-menu-item>
      <el-menu-item index="teacher" >
        <i class="el-icon-coin"></i>
        <span slot="title">教师管理</span>
      </el-menu-item>
      <el-menu-item index="card" >
        <i class="el-icon-coin"></i>
        <span slot="title">账户管理</span>
      </el-menu-item>
      <el-menu-item index="shop">
        <i class="el-icon-money"></i>
        <span slot="title">食堂管理人员管理</span>
      </el-menu-item>
      <el-menu-item index="admin" v-if="this.admin.pro === '超级管理员'">
      <i class="el-icon-key"></i>
        <span slot="title">管理人员处理</span>
      </el-menu-item>





    </el-menu>
  </el-col>
  </div>


     <div :class="isCollapse?'small':'right'">
        <div class="label">


            <span class="label-left">
              <el-button v-if="isCollapse" icon="el-icon-s-unfold" style="background-color:transparent;border:0;font-size: 24px" @click="isCollapse = false"></el-button>
              <el-button v-else icon="el-icon-s-fold" style="background-color:transparent;border:0;font-size: 24px" @click="isCollapse = true"></el-button>

                <el-breadcrumb separator="/">
                  <el-breadcrumb-item v-for="item in breadList">{{item}}</el-breadcrumb-item>
                </el-breadcrumb>

            </span>
            <span class="title">智慧校园管理系统</span>
            <span style="line-height: 55px;float:right">
              <el-dropdown>
                  <span class="el-dropdown-link">
                    {{admin.name}}<i class="el-icon-arrow-down el-icon--right"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="$router.push('me')">个人信息</el-dropdown-item>
                    <el-dropdown-item @click.native="loginout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
            </span>



        </div>
        <router-view class="view"></router-view>

     </div>
    </div>


</template>


<style scoped>

.left{
  position: fixed;
  left:0;
  top:0;
  bottom:0;
  background-color: rgb(239, 247, 128);
}

.body{

}


.el-menu{
  height:100%
}


.right{

  padding-left: 200px;

}

.label{

   height: 55px;
   background-color: pink;
   border-bottom: black 1px solid;
   display: block;

}
.label-left{
  line-height: 55px;
  display: flex;
  float: left;

}

.el-breadcrumb{
  line-height: 55px;
  font-size: 20px;
}

.label_title{
  text-align: center;
  color:black;
  font-weight: bold;
  line-height: 50px;
  font-size:30px;

}

.label-right{
    float: right;
    text-align: right;
}

.el-dropdown-link {
  cursor: pointer;
  color: black;
  font-size: 23px;
  font-family: "微软雅黑";
}
.el-icon-arrow-down {
  font-size: 18px;
}

.el-menu{
  border-right: 0;
}

.is-active{
  background-color: skyblue !important;
  color:white;
}

.el-menu-vertical-demo:not(.el-menu--collapse){
  width:200px;
  min-height: 400px;
}

.small{
  padding-left: 64px;
}

.title{
  position: absolute;
  left:900px;
  line-height: 55px;
  text-align: center;
  color:black;
  font-weight: bold;
  font-size:30px;
}

.el-menu-item{
  font-size: 18px;
}

.view{
  padding-left: 20px;
}

.cell{
  text-align: center;
  font-size: 20px;
  color: black;
  font-family: 微软雅黑;
}

.aaaa{
  background-color: #f8f8f8;
  height:100vh;
}
</style>

<script>

export default {
  data() {
    return {
      admin: {
        id: "",
        name: "",
        pro: ""
      },
      isCollapse: false,
      default_active: this.$route.path.split("/")[2],
      breadList:[],

    }
  },


  created() {
    console.log(this.$route.matched)


    if(sessionStorage.getItem("admin") == null){
      alert("请登录后使用")
      this.$router.push('login')
    }
    else{
      console.log(sessionStorage.getItem("admin"))
    this.admin = JSON.parse(sessionStorage.getItem("admin"))



    console.log(sessionStorage.getItem('navkeep'))
    if(sessionStorage.getItem('navkeep')){
        this.active=sessionStorage.getItem('navkeep');
      }

    }




  },

  methods: {
    keep(e) {
      this.active = e;
      console.log(this.active)
      sessionStorage.setItem('navkeep', this.active);
    },

    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    loginout() {
      sessionStorage.removeItem("admin");
      this.$message.success("退出成功")
      this.$router.push('login')


    },





  },

  watch:{
    $route:{
      handler(route){
        console.log("13")
        this.breadList = []
        let List = route.matched;
        console.log(List)
        List.forEach(item => {
          this.breadList.push(item.meta.title);

        });
      },immediate:true
    }
  }



}
</script>
