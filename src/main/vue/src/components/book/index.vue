<template>
    <div class="body">
      <div class="top_label">
        <div>图书漂流管理系统</div>

      </div>

      <div class="content">
        <div class="left_label">
          <el-col :span="3">
            <el-menu
                ref="menu"
                router
                style="position:absolute ;height: 75vh;width:200px"
                background-color="lightyellow"
                default-active="front"
                class="el-menu-vertical-demo"

            >

              <el-menu-item index="front">

                <span slot="title">首页</span>
              </el-menu-item>

              <el-menu-item index="student">

                <span slot="title">学生管理</span>
              </el-menu-item>
              <el-menu-item index="card" >

                <span slot="title">学生一卡通管理</span>
              </el-menu-item>
              <el-menu-item index="admin">

                <span slot="title">管理人员处理</span>
              </el-menu-item>

              <el-menu-item index="lost">

                <span slot="title">挂失处理</span>
              </el-menu-item>

              <el-menu-item index="me">
                <span slot="title">个人管理</span>

              </el-menu-item>
            </el-menu>
          </el-col>
        </div>

        <div class="book">
          <table>
            <tr v-for="i in book_list">

              <td>
                <img :src="i.imgSrc">
                <div>{{i.name}}</div>
                <div>￥{{i.price}}</div>
              </td>
            </tr>
          </table>
        </div>

        <div class="me">

          <div v-if="is_login === false" style="margin:200px auto">
            <div>使用图书漂流功能需要登录!</div> <br>
            <el-input placeholder="请输入学号" v-model="stu.id"></el-input>
            <el-input placeholder="请输入密码" type="password" v-model="stu.pwd"></el-input>
            <el-button style="margin:0px auto;display: block" @click="login">账号密码登录</el-button>
            <el-button style="margin:0px auto;display: block">刷脸登录</el-button>
          </div>

          <!-- 当登录成功 -->
          <div v-if="is_login === true" style="margin:100px auto">
            <img src="#" style="margin:100px auto;display: block">
            <div>{{stu.name}}</div>
            <div>拥有读书金币:{{stu_book.coin}}</div>
            <el-button style="background-color:transparent;border: none">我捐赠的书籍</el-button>
            <el-button style="background-color:transparent;border: none">我领取的书籍</el-button>

            <br>
            <el-button type="primary">我要捐赠</el-button> <br>

            <el-button type="danger">退出登录</el-button>


            <div></div>
          </div>

        </div>

      </div>

    </div>
</template>

<script>
import axios from "axios"
export default {

  name: "index",
  data(){
    return{
      book:{
        page:1,
        size:8,
      },
      stu:{
        id:"",
        pwd:"",
      },

      stu_book:{

      },

      book_list : [],

      is_login:false,

    }
  },

  created(){
    axios.get("book/menu?page=" + this.book.page + "&size=" + this.book.size).then(res=>{
      console.log(res.data)
      this.book_list = res.data.records;
    })
  },

  methods:{
    book(){

    },

    login(){
      let param = new URLSearchParams();
      param.append("id",this.stu.id)
      param.append("pwd",this.stu.pwd)
      axios.post("student/login",param).then(res=>{
        console.log(res);
        if(res.data.length !== 0){
          alert("登录成功")
          this.is_login = true;
          this.stu = res.data[0];
          axios.post("stubook?id=" + this.stu.id).then(res=>{
            this.stu_book = res.data
          })
        }
      })
    },


  }
}


</script>

<style scoped>
  .body{
    background-color: lightgreen;
    width:200vh;
    height:80vh;

  }


  .top_label{
    height:50px;
    background-image: linear-gradient(to right,skyblue,lightseagreen);
    line-height: 50px
  }

  .left_label{
    width:20%;
    height:100vh;
    float:left;
    display: inline-block;
  }

  .book{
    width:50%;
    height:100vh;
    display: inline-block;
  }

  .me{
    width:20%;
    height:80vh;
    float:right;
    text-align: center;
    background-color:lightgoldenrodyellow;
  }

  .content{
    width: 100%;
    height: 100%;

  }

  .el-input{
    width: 240px;
    margin:0 auto;
  }

</style>
