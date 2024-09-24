<template>
<div>
  <el-button @click="back">返回</el-button>
  <el-menu  default-active="1" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <el-menu-item index="1">已考勤({{come}})</el-menu-item>
    <el-menu-item index="0">未考勤({{no_come}})</el-menu-item>

  </el-menu>


  <!-- 表格 -->
  <el-table
      :data="tableData"
      style="width: 100%;color:black;font-size:20px;font-family:微软雅黑">
    <el-table-column
        prop="student.id"
        label="学号"
        width="180">
    </el-table-column>
    <el-table-column
        prop="student.imgSrc"
        label="照片"
        width="180">
      <template slot-scope="scope">
        <img :src="'http://localhost:8088/download?name=' + scope.row.student.imgSrc">
      </template>
    </el-table-column>

    <el-table-column
        prop="student.classes"
        label="班级"
        width="180">
    </el-table-column>
    <el-table-column
        prop="student.name"
        label="姓名"
        width="180">
    </el-table-column>
    <el-table-column
        prop="checks"
        label="状态"
        width="180">

      <template slot-scope="scope">

        <el-tag v-if="scope.row.checks === 0" type="danger" >
          未考勤
        </el-tag>

        <el-tag v-if="scope.row.checks === 1" type="success" >
          已考勤
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column
        prop="time"
        label="时间"
        width="250">
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
import axios from 'axios'
export default {
  name: "class_check",
  data(){
    return{
      page:1,
      size:5,
      tableData:"",
      no_come:0,
      come:0,
      total:0,
    }
  },

  created() {
    this.class_id = this.$route.params.id
    this.req(1)
  },




  methods:{
    handleSelect(e){
      this.req(e)
    },

    req(check){
      axios.get("classes/page_total?class_id=" + this.class_id + "&checks=" + check).then(res=>{
        this.total = res.data
      })

      axios.get("classes/page_total?class_id=" + this.class_id + "&checks=0").then(res=>{
        this.no_come = res.data
      })

      axios.get("classes/page_total?class_id=" + this.class_id + "&checks=1").then(res=>{
        this.come = res.data
      })

      axios.get("classes/page_list?class_id=" + this.class_id + "&checks=" + check + "&page=" + this.page + "&size=" + this.size  ).then(res=>{
        console.log(res.data)
        this.tableData = res.data
      })
    },

    back(){
      this.$router.push("../classes")
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
