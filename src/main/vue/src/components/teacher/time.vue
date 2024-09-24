<template>

  <div>



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
          prop="name"
          label="姓名"
          width="180">
      </el-table-column>

      <el-table-column
          prop="state"
          label="状态"
          width="180">
      </el-table-column>


      <el-table-column
          prop="time"
          label="时间"
          width="180">
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

      state:"1",
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
</style>
