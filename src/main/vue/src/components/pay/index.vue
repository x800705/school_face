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
        <el-menu-item index="/pay/pay_it">
            <i class="el-icon-location"></i>
            <span>点单</span>
        </el-menu-item>
        <el-menu-item index="/pay/bill">
          <i class="el-icon-menu"></i>
          <span slot="title">订单</span>
        </el-menu-item>
        <el-menu-item index="/pay/money">
          <i class="el-icon-document"></i>
          <span slot="title">收入管理</span>
        </el-menu-item>
        <el-menu-item index="/pay/admin">
          <i class="el-icon-setting"></i>
          <span slot="title">菜品管理</span>
        </el-menu-item>
        <el-menu-item index="/pay/type">
          <i class="el-icon-files"></i>
          <span slot="title">分类管理</span>
        </el-menu-item>
        <el-menu-item index="/pay/user">
          <i class="el-icon-user"></i>
          <span slot="title">个人信息</span>
        </el-menu-item>
      </el-menu>

  </div>

    <div :class="isCollapse?'small':'right'">
      <div class="title_label">
      <div class="label-left">

        <el-button v-if="isCollapse" icon="el-icon-s-unfold" style="background-color:transparent;border:0;font-size: 24px" @click="isCollapse = false"></el-button>
      <el-button v-else icon="el-icon-s-fold" style="background-color:transparent;border:0;font-size: 24px" @click="isCollapse = true"></el-button>

      </div>

        <span style="line-height: 55px;float:right">
              <el-dropdown>
                  <span class="el-dropdown-link">
                    {{business.name}}<i class="el-icon-arrow-down el-icon--right"></i>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item @click.native="$router.push('user')">个人信息</el-dropdown-item>
                    <el-dropdown-item @click.native="loginout">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
            </span>

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
     this.business = JSON.parse((sessionStorage.getItem("business")))
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

.is-active{
  background-color: #343333 !important;
  color: white !important;

}

.label-left{
  line-height: 55px;
  display: flex;
  float: left;

}


</style>
