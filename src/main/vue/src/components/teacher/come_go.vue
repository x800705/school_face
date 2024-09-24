<template>
<div>
  <!--导航栏 -->
  <el-menu  default-active="10" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <el-menu-item index="10">全部记录</el-menu-item>
    <el-menu-item index="1">到校</el-menu-item>
    <el-menu-item index="0">离校</el-menu-item>
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
        prop="name"
        label="姓名"
        width="180">
    </el-table-column>


    <el-table-column
        prop="time"
        label="时间"
        width="220">
    </el-table-column>


    <el-table-column
        prop="state"
        label="操作"
        width="220">
      <template slot-scope="scope">
        <el-tag v-if="scope.row.state === 1" type="success" >
          入校
        </el-tag>

        <el-tag v-if="scope.row.state === 0" type="danger" >
          出校
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
  name: "come_go",
  data(){
    return{
      check_in:{
        page:"1",
        size:"5",
        state:"10",
      },

      tableData:"",
      size:5,
    }
  },

  created() {
    this.teacher = JSON.parse((sessionStorage.getItem("teacher")))
    this.check_in.classes = this.teacher.classes
    this.req();

  },

  methods:{
    handleSelect(e){
      this.check_in.state = e
      this.check_in.page = 1
      this.req();
    },

    handleSizeChange(e){
      this.check_in.size = e;
      this.req();
    },

    handleCurrentChange(e){
      this.check_in.page = e;
      this.req();
    },

    req(){
      axios.post("checkin/page",this.check_in).then(res=>{
       this.tableData = res.data.records
        this.total = res.data.total
      })
    }
  }

}
</script>

<style scoped>

</style>
