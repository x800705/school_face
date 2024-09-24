<template>
<div>

  <!--导航栏 -->
  <el-menu  default-active="10" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <el-menu-item index="10">全部记录</el-menu-item>
    <el-menu-item index="0">待批假</el-menu-item>
    <el-menu-item index="1">已批假</el-menu-item>
    <el-menu-item index="-1">已拒绝</el-menu-item>
  </el-menu>

  <!-- 表格 -->
  <el-table
      :data="tableData"
      style="width: 100%;color:black;font-size:20px;font-family:微软雅黑">
    <el-table-column
        prop="stuId"
        label="学号"
        width="180">
    </el-table-column>

    <el-table-column
        prop="name"
        label="姓名"
        width="100">
    </el-table-column>


    <el-table-column
        prop="start"
        label="请假开始时间"
        width="220">
    </el-table-column>


    <el-table-column
        prop="end"
        label="请假结束时间"
        width="220">
    </el-table-column>

    <el-table-column
        prop="reason"
        label="理由"
        width="180">
    </el-table-column>

    <el-table-column
        label="请假状态"
        width="180">
      <template slot-scope="scope">

        <el-tag v-if="scope.row.accept === 0" type="info" >
          待批假
        </el-tag>

        <el-tag v-else-if="scope.row.accept === 1" type="success" >
          已通过
        </el-tag>


        <el-tag v-else-if="scope.row.accept === -1" type="danger" >
          已拒绝
        </el-tag>

      </template>
    </el-table-column>


    <el-table-column
        label="操作"
        width="220">
      <template slot-scope="scope">
        <div v-if="scope.row.accept === 0">
           <el-button type="success" @click="change_sick(scope.row.id,1)">通过</el-button>
           <el-button type="danger" @click="change_sick(scope.row.id,-1)">拒绝</el-button>
        </div>
        <div v-else>
          <el-button type="danger" @click="del_sick(scope.row.id)">删除</el-button>
        </div>


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
  name: "sick",
  data(){
    return{
      page:"1",
      size:"5",
      accept:"10",
      classes:"",


      tableData:"",
      total:"",

      sick:{},

    }
  },

  created() {
    this.teacher = JSON.parse((sessionStorage.getItem("teacher")))
    this.classes = this.teacher.classes
    this.req();
  },

  methods:{
    handleSelect(e){
       this.accept = e;
       this.page = 1;
       this.size = 5;
       this.req();
    },

    req(){      /*
      let param = new URLSearchParams();

      param.append("imgSrc", this.student.imgSrc);
      param.append("id", this.student.id);
      param.append("name", this.student.name);
      param.append("classes", this.student.classes);
      param.append("pwd", this.student.id.substring(this.student.id.length - 6, this.student.id.length));

       */
      //输入 班级、状态   返回 Page

      let param = new URLSearchParams();
      param.append("page",this.page );
      param.append("size",this.size );
      param.append("accept",this.accept );
      param.append("classes",this.classes );



      axios.post("sick/page",param).then(res=>{
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    handleSizeChange(e){
      this.size = e;
    },

    handleCurrentChange(e){
      this.page = e;
    },

    change_sick(id,e){
      this.sick.id = id;
      this.sick.accept = e;

      this.$forceUpdate();

      axios.post("sick/change",this.sick).then(res=>{
        console.log(res.data);
        this.$message.success("操作成功")
        if(e === 1){
          this.sick.state = -1
          this.sick.accept = null
          this.$forceUpdate();
          axios.post("student/sick",this.sick).then(res=>{

          })
        }
        this.req();
      })
    },

    del_sick(id){
      axios.post("sick/del/" + id).then(res=>{
        console.log(res.data)
      })
    }

  }
}
</script>

<style scoped>

</style>
