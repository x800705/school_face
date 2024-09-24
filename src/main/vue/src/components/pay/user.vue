<template>
<div>
  <table>

    <tr>
      <td>商户号：</td>
      <td>{{business.id}}</td>
    </tr>

    <tr>
      <td>经营者姓名：</td>
      <td>{{business.name}}</td>
    </tr>



    <tr>
      <td>手机号：</td>
      <td>{{business.phone}}</td>
    </tr>


  </table>



  <el-button type="primary" @click="new_business.id = business.id;userVisible = true">修改基本信息</el-button>
  <el-button type="primary" @click="phoneVisible = true">修改绑定手机号</el-button>


  <el-dialog
      title="修改绑定手机号"
      :visible.sync="phoneVisible"
      width="30%">

  <el-form :rules="rules" :model="new_business">
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="new_business.phone" placeholder="修改手机号"></el-input>
    </el-form-item>

    <el-form-item label="验证码" prop="code">
    <el-input v-model="new_business.code" placeholder="填写验证码"></el-input>
    </el-form-item>

    <el-button @click="get_code" type="primary">获取验证码</el-button> <br> <br>
    <el-button @click="phoneVisible = false;pwd = ''" type="danger" style="width:45%;">取 消</el-button>
    <el-button @click="change_phone" type="success" style="width:45%; ">提交</el-button>
  </el-form>

  </el-dialog>

  <el-dialog
      title="修改个人信息"
      :visible.sync="userVisible"
      width="30%">

    <el-form :rules="rules" :model="new_business">
      <el-form-item label="经营者姓名" prop="name">
        <el-input v-model="new_business.name" placeholder="修改经营者姓名"></el-input>
      </el-form-item>

      <el-form-item label="商店名" prop="shopName">
        <el-input v-model="new_business.shopName" placeholder="修改商店名"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="pwd">
        <el-input v-model="new_business.pwd" placeholder="修改密码" type="password"></el-input>
      </el-form-item>

      <el-button @click="userVisible = false;" type="danger" style="width:45%;">取 消</el-button>
      <el-button @click="change" type="success" style="width:45%; ">提交</el-button>
    </el-form>

  </el-dialog>





</div>
</template>

<script>
import axios from 'axios'
export default {

  data(){
    return{
      business:"",
      new_business:{

      },

      phoneVisible:false,
      userVisible:false,
      rules:{
        phone:[
          {required:true,message:"手机号不能为空",trigger:'blur'},
          {min:11,max:11,message: "请输入正确的电话号码",trigger: 'blur'}
        ],
        code:[
          {required:true,message:"验证码不能为空",trigger:'blur'}
        ]
      },
    }

  },


  created() {
    this.business = JSON.parse((sessionStorage.getItem("business")))
    if(this.business.phone !== "") this.business.phone = this.business.phone.slice(0,3) + "****" + this.business.phone.slice(7,11)


  },

  methods:{

    get_code(){
      if(this.new_business.phone.length === 11){

        axios.post("business/get_code?phone=" + this.new_business.phone).then(res=>{
          console.log(res);
          if(res.data === "success"){
            alert("发送成功")
          }
          else{
            alert("发送失败")
          }


        })


      }


    },

    change_phone(){
      if(this.new_business.code !== ""){
        axios.post("business/change_phone?phone=" + this.new_business.phone + "&id=" + this.business.id  + "&code=" + this.new_business.code).then(res =>{

          console.log(res);
          if(res.data !== ""){
            alert("修改成功")
            this.$router.go(0)
          }
        })
      }


    },

    change(){
      axios.post("business/change",this.new_business).then(res=>{
        console.log(res)
        if(res.data === 1){
          this.$message.success("修改成功")

          axios.get("business/getOne?id=" + this.business.id).then(res=>{
            this.business = res.data

            sessionStorage.setItem("business",JSON.parse(this.business))
            if(this.business.phone !== "") this.business.phone = this.business.phone.slice(0,3) + "****" + this.business.phone.slice(7,11)
            this.userVisible = false

          })

        }
      })
    }



  }







}
</script>

<style scoped>
tr,td{
  padding:10px
}
</style>
