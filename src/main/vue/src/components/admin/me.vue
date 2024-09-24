<template>
    <div>
        <h3>
            个人资料
        </h3>
      <table>
        <tr>
          <td>管理员编号:</td> <td>{{admin.id}}</td>
        </tr>
        <tr>
          <td>姓名:</td> <td>{{admin.name}}</td>
        </tr>
        <tr>
          <td>管理权限:</td> <td>{{admin.pro}}</td>
        </tr>
        <tr><el-button type="primary" @click="dialogVisible = true;">修改密码</el-button></tr>
      </table>


      <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="30%">
        <el-input v-model="pwd" placeholder="请输入新密码" type="password"></el-input>
    <el-button @click="dialogVisible = false;pwd = ''">取 消</el-button>
    <el-button type="primary" @click="change_pwd">确 定</el-button>

      </el-dialog>


    </div>
</template>

<script>

import axios from "axios"
export default {
  data(){
    return{
      admin:{

      },
      dialogVisible:false,
      pwd:"",

    }
  },

  created(){
    this.admin = JSON.parse(sessionStorage.getItem("admin"))
    console.log(this.admin)
  },

  methods:{
    change_pwd(){
      this.admin.pwd = this.pwd
      axios.post("admin/change_pwd",this.admin).then(res=>{
        if(res.data === 1){
          this.$message.success("修改成功")
          this.dialogVisible = false
        }
        else{
          this.$message.success("修改失败")
        }

      }).catch(res=>{
        this.$message.error("服务器有些累了，等会儿再试试吧")
      })

      this.pwd = ""
    }
  }

}

</script>

<style>

tr,td{
  padding:10px
}
</style>
