<template>
  <div>
    <!--
    <div style="background-color:skyblue">
      <div style="padding: 10px 0px"> {{business.shopName}} <div style="float:right" > 欢迎回来,{{business.name}}<el-button @click="loginout()" >退出登录</el-button></div></div>

    </div>

    -->
    <div class="label">

      <el-menu

          router
          height="100%"
          :default-active=this.$route.path
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          :collapse=isCollapse>
        <el-menu-item style="pointer-events:none">
          <span>教师管理系统</span>
        </el-menu-item>
        <el-menu-item index="/teacher/check_it">
          <i class="el-icon-location"></i>
          <span>考勤管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher/sick">
          <i class="el-icon-menu"></i>
          <span slot="title">请假管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher/come_go">
          <i class="el-icon-menu"></i>
          <span slot="title">出入管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher/classes">
          <i class="el-icon-menu"></i>
          <span slot="title">课堂考勤管理</span>
        </el-menu-item>

      </el-menu>

    </div>

    <div :class="isCollapse?'small':'right'">

      <div class="title_label">

        <div class="label-left">
          <el-button v-if="isCollapse" icon="el-icon-s-unfold" style="background-color:transparent;border:0;font-size: 20px" @click="isCollapse = false"></el-button>
          <el-button v-else icon="el-icon-s-fold" style="background-color:transparent;border:0;font-size: 24px" @click="isCollapse = true"></el-button>
        </div>

        <span style="line-height: 55px;float:right">
              <el-dropdown>
                  <span class="el-dropdown-link">
                    {{teacher.name}}<i class="el-icon-arrow-down el-icon--right"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="$router.push('me')">个人信息</el-dropdown-item>
                    <el-dropdown-item @click.native="loginout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
            </span>

        <div>

        </div>


      </div>






      <router-view class="view"></router-view>

    </div>

    <!--
  <div style="float: right;width:90%">
    <router-view></router-view>
  </div>
  -->

  </div>





</template>

<script>
export default {

  data(){
    return{
      business:{

      },
      isCollapse:false
    }

  },

  created(){
    this.teacher = JSON.parse((sessionStorage.getItem("teacher")))

    if(this.business === null){
      alert("你应该先登录")
      this.$router.push("login")
    }


  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },

    loginout() {
      alert("退出登录成功")
      sessionStorage.clear("business");
      this.$router.push("login")
    }
  }
}
</script>
<style scoped>
.el-menu{
  height: 100vh;
}

.title{

}

.right{
  padding-left: 200px;

}

.small{
  padding-left: 64px;

}

.is-active{
  background-color: #808080 !important;
  color: white !important;

}

.el-dropdown-link {
  cursor: pointer;
  color: black;
  font-size: 25px;
  font-family: "微软雅黑";
}

.el-menu-vertical-demo:not(.el-menu--collapse){
  width:200px;
  min-height: 400px;
}

.label{
  position: fixed;
  top:0;
  bottom: 0;
  left:0;
}

.title_label {
  height: 60px;
  width: 100%;
  background-color: skyblue;
  line-height: 60px;
}

.view{
  padding-left: 20px;
}

.el-menu{
  border-right: 0;
}

.label-left{
  line-height: 55px;
  display: flex;
  float: left;

}

.cell{
  color:black;
  font-size: 25px;
}


</style>
