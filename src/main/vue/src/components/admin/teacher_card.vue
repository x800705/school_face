<template>
  <div>
    <h3>教师一卡通管理</h3>

    <div>
      <el-select v-model="value" placeholder="请选择" style="width: 10%">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

      <el-input v-model="content" placeholder="请输入搜索内容" style="width: 30%"></el-input>
      <el-button type="primary" @click="search()">提交</el-button>
      <el-button type="primary" @click="reflash()">刷新</el-button>
      <el-table
          :data="tableData"
          style="width: 100%">
        <el-table-column
            prop="id"
            label="学号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="student.name"
            label="姓名"
            width="180">
        </el-table-column>
        <el-table-column
            prop="student.classes"
            label="班级"
            width="180">
        </el-table-column>
        <el-table-column
            prop="money"
            label="金额"
            width="180">
        </el-table-column>
        <el-table-column
            prop="ban"
            label="是否异常"
            width="180">
        </el-table-column>

        <el-table-column label="操作" width="360">
          <template slot-scope="scope">
            <el-button type="success" @click="add(scope.row)">充值</el-button>
            <el-button type="primary" @click="change(scope.row)">更改</el-button>
            <el-button v-if="scope.row.ban === 0" type="danger" @click="ban(scope.row.id)">挂失</el-button>
            <el-button v-else type="success" @click="no_ban(scope.row.id)">解除挂失</el-button>

          </template>
        </el-table-column>

      </el-table>

      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="current_page"
          :page-sizes="[1,2,5]"
          :page-size="page_size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>


      <el-dialog title="更改余额" :visible.sync="dialogFormVisible">
        <el-form>
          <el-form-item label="学号">
            <el-input v-model="id" autocomplete="off" disabled></el-input>
          </el-form-item>

          <el-form-item label="姓名">
            <el-input v-model="name" autocomplete="off" disabled></el-input>
          </el-form-item>

          <el-form-item label="更改金额">
            <el-input v-model="money" autocomplete="off"></el-input>
          </el-form-item>

        </el-form>

        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="rechange()">确 定</el-button>
      </el-dialog>



      <el-dialog title="充值余额" :visible.sync="dialogFormaddVisible">
        <el-form>
          <el-form-item label="学号">
            <el-input v-model="id" autocomplete="off" disabled></el-input>
          </el-form-item>

          <el-form-item label="姓名">
            <el-input v-model="name" autocomplete="off" disabled></el-input>
          </el-form-item>

          <el-form-item label="充值金额">
            <el-input v-model="money" autocomplete="off"></el-input>
          </el-form-item>

        </el-form>

        <el-button @click="dialogFormaddVisible = false">取 消</el-button>
        <el-button type="primary" @click="readd()">确 定</el-button>
      </el-dialog>









    </div>

  </div>






</template>

<script>
import axios from 'axios';

export default {

  data() {
    return {

      formLabelWidth: '120px',
      dialogFormVisible: false,
      dialogFormaddVisible:false,
      id:"",
      money:"",
      name:"",
      tableData: [],
      current_page:1,
      total:40,
      page_size:5,
      options: [{
        value: 'id',
        label: '学号'
      }, {
        value: 'name',
        label: '姓名'
      }, {
        value: 'classes',
        label: '班级'
      }],
      value: 'id',
      content: '',


    }
  },



  created(){  // 创建函数

    this.req()

  },


  methods: {
    handleSizeChange(val) { // 分页变化
      this['page_size'] = val

      this.req();


    },


    handleCurrentChange(val) { // 页数变化
      console.log(val)
      this['current_page'] = val

      this.req();



    },

    del(scope){
      console.log(scope);

      if(confirm("你确定要删除")){
        axios.get("/student/DelOne/" + scope).then((responds) =>{
          console.log(responds);

          alert("已删除")
          this.$router.go(0)
        })
      }
      else{

        alert("删除请求撤回");
      }
    },

    change(res){
      this.dialogFormVisible = true
      this.id = res.id
      this.name = res.name
    },

    rechange(){
      let param = new URLSearchParams();
      var date = new Date();
      var date = date.getFullYear() + "-" + date.getMonth() + 1 + "-" + date.getDate()
      param.append("id",this['id']);
      param.append("money",this['money']);
      param.append("date",date);

      axios.post("money/change_stu",param).then((responds) =>{
        console.log(responds);
      })

      this.$message.success("更改成功")
      this.dialogFormVisible = false
      this.req()

      //this.$router.go(0);
    },

    add(res){
      this.dialogFormaddVisible = true
      this.id = res.id
      this.name = res.student.name
    },

    readd(){
      let param = new URLSearchParams();
      var date = new Date();
      var date = date.getFullYear() + "-" + date.getMonth() + 1 + "-" + date.getDate()
      param.append("id",this['id']);
      param.append("money",this['money']);
      param.append("date",date);
      param.append("content","充值")

      axios.post("money/add_stu",param).then((responds) =>{
        console.log(responds);
      })

      this.$message.success("充值成功")
      this.dialogFormaddVisible= false
      this.req()
    },

    search(){
      this.req();
    },

    req(){

      axios.get("money/count_stu").then((res)=>{

        this.total = res.data;

      })

      axios.get("money/new_search",{
        params:{
          num:this.page_size,
          page:this.current_page,
          content:this.content,
          methon:this.value,


        }
      }).then((responds) =>{
        console.log("get")
        console.log(responds);

        this.tableData = responds.data;
      })
    },
    reflash(){
      this.req()
    },

    ban(id){
      let param = new URLSearchParams();
      param.append("id",id);
      param.append("is_ban",1);
      axios.post('/money/ban',param).then((res) =>{
        console.log(res)

        this.$message({
          message: '挂失成功',
          type: 'success'

        });
        this.req()
      }).catch((res)=>{
        this.$message.error('挂失失败');
      })
    },

    no_ban(id){
      let param = new URLSearchParams();
      param.append("id",id);
      param.append("is_ban",0);
      axios.post('/money/ban',param).then((res) =>{
        console.log(res)

        this.$message({
          message: '解除挂失成功',
          type: 'success'

        })
        this.req()
      }).catch((res)=>{
        this.$message.error('解除挂失失败');
      })
    }





  }

}



</script>

<style>

.cell{
  color:black;
  font-size:20px;
}
</style>
