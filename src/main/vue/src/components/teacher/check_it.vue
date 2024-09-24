<template>

  <div>
    <!--导航栏 -->
    <el-menu  default-active="null" class="el-menu-demo" mode="horizontal" @select="handleSelect">

      <el-menu-item index="null">全部学生</el-menu-item>
      <el-menu-item index="1">已到校</el-menu-item>
      <el-menu-item index="0">未到校</el-menu-item>
      <el-menu-item index="-2">待批假</el-menu-item>
      <el-menu-item index="-1">请假中</el-menu-item>
    </el-menu>


    <!-- 表格 -->
    <el-table
        :data="tableData"
        style="width: 100%;color:black;font-size:20px;font-family:微软雅黑">
      <el-table-column
          prop="id"
          label="学号"
          width="180">
      </el-table-column>
      <el-table-column
          prop="imgSrc"
          label="照片"
          width="180">
        <template slot-scope="scope">
          <img :src="'http://localhost:8088/download?name=' + scope.row.imgSrc">
        </template>
      </el-table-column>

      <el-table-column
          prop="classes"
          label="班级"
          width="180">
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          width="180">
      </el-table-column>
      <el-table-column
          prop="state"
          label="状态"
          width="180">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state === -1" type="warning" >
            请假中
          </el-tag>
          <el-tag v-if="scope.row.state === -2" type="config" >
            待批假
          </el-tag>
          <el-tag v-if="scope.row.state === 0" type="danger" >
            未到校
          </el-tag>
          <el-tag v-if="scope.row.state === 1" type="success" >
            已到校
          </el-tag>
        </template>
      </el-table-column>






    </el-table>

    <!-- 分页 -->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[1,2,5,10]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>


  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "check_it",
  data(){
    return{
      //需要传的值

        state:"null",
        page:"1",
        size:"5",
        total:"10",
        tableData:"",

        teacher:{

        },


    }
  },

  created(){
     this.teacher = JSON.parse((sessionStorage.getItem("teacher")))
     console.log(this.teacher)
     this.req();
  },

  methods:{
    //学生状态赋值
    handleSelect(e){
        this.state = e;
        this.page = 1;
        this.size = 5;
        this.req();
    },


    req(){
      axios.get("student/page",{
        params:{
           state:this.state,
           page:this.page,
           size:this.size,
           classes:this.teacher.classes,
        }
      }).then(res=>{
        this.tableData = res.data.records
        this.total = res.data.total


      })
    },

    handleSizeChange(e){
      this.size = e;
    },

    handleCurrentChange(e){
      this.page = e;
    }
  }
}
</script>

<style scoped>

img{
  width: 60px;
  height: 80px;
}

.cell{
  text-align: center;
  font-size: 23px;
  color: black;
}

thead{
  color: black;
}
</style>
