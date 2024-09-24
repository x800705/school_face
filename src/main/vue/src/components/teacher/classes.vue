<template>
<div>
  <div>

    <el-button type="primary" @click="dialogFormVisible = true">添加课程</el-button>


    <!-- 表格 -->
    <el-table
        :data="tableData"
        style="width: 100%;color:black;font-size:20px;font-family:微软雅黑">
      <el-table-column
          prop="name"
          label="课程名"
          width="180">
      </el-table-column>

      <el-table-column
          prop="classes"
          label="教授班级"
          width="180">
      </el-table-column>





      <el-table-column
          prop="time"
          label="时间"
          width="220">

        </el-table-column>

        <el-table-column
            label="操作"
            width="350">


          <template slot-scope="scope">
            <el-button v-if="scope.row.checks === '0'" type="success" @click="check_change(scope.row,1)">开始考勤</el-button>
            <el-button v-if="scope.row.checks === '1'" type="danger" @click="check_change(scope.row,0)">关闭考勤</el-button>
            <el-button @click="detail(scope.row.id)">考勤详情</el-button>
            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
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

    <!-- 添加学生提示框 -->

    <el-dialog title="添加学生" :visible.sync="dialogFormVisible">
      <el-form>



        <el-form-item label="课程名">
          <el-input v-model="classes.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="班级名">
          <el-input v-model="classes.classes" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="时间">
          <el-input type="datetime-local" v-model="classes.time" autocomplete="off"></el-input>
        </el-form-item>




      </el-form>

      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="add()" :loading="loading">确 定</el-button>
    </el-dialog>




  </div>

</div>
</template>

<script>
import axios from "axios"
export default {
  name: "classes",
  data(){
    return{
       classes: {
         page: "1",
         size: "5",
         name:"",
         classes:"",
         time:""
       },
      tableData:"",
      total:"5",
      dialogFormVisible:false
    }
  },

  created() {
    this.req()
  },

  methods:{
    req(){
      axios.post("classes/page",this.classes).then(res=>{
          this.tableData = res.data.records
          this.total = res.data.total

      })
    },

    check_change(row,e){
      axios.post("classes/change_check?id=" + row.id + "&checks=" + e).then(res=>{
        console.log(res)
      })

      if(e === 1){
        axios.post("classes/add_list?id=" + row.id + "&classes=" + row.classes).then(res=>{
          console.log(res)
        })

        this.$message.success("现在开始考勤")
      }

      if(e === 0){
        this.$message.success("考勤已关闭")
      }

      this.req()

    },

    detail(id){

      this.$router.push("classes/" + id)
    },

    add(){
      this.classes.time = this.classes.time.replace("T"," ")

      axios.post("classes/add",this.classes).then(res=>{
        console.log(res)
        this.$message.success("添加成功")
        this.dialogFormVisible = false
        this.req()
      })
    },

    del(e){
      axios.post("classes/del/" + e).then(res=>{
        console.log(res)
        this.$message.success("删除成功")
        this.req()
      })
    }



  },



}
</script>

<style scoped>

</style>
