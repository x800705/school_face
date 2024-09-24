<template>
    <div>
    <h3>挂失处理</h3>

         <!-- 搜索框 -->
     <div>
          <!--选择栏 -->
          <el-select v-model="value" placeholder="请选择" style="width: 15%">
          <el-option
               v-for="item in options"
               :key="item.value"
               :label="item.label"
               :value="item.value">
          </el-option>
          </el-select>

   <!--输入栏 -->
   <el-input v-model="content" placeholder="请输入搜索内容" style="width: 30%"></el-input>
   <!-- 提交按钮 -->
   <el-button type="primary" @click="search()">提交</el-button>



<el-button type="primary" @click="adds()">添加管理员</el-button>
  </div>

       <!-- 表格 -->
     <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="学号"
        width="180">
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


      <el-table-column label="操作" width="360">
          <template slot-scope="scope">
              <el-button type="success" @click="no_ban(scope.row.id)">解除挂失</el-button>
          </template>
      </el-table-column>

    </el-table>

     <!-- 分页 -->
       <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="current_page"
      :page-sizes="[1,2,5,10]"
      :page-size="page_size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>



    </div>
</template>

<script>
import search from '../component/search.vue'
import axios from 'axios'
export default{
components: {
    search
  },

data(){
    return{
        options: [{
          value: 'id',
          label: '管理员编号'
        }, {
          value: 'name',
          label: '姓名'
        },],
        value: 'id',
        content: '',
        total:0,
        tableData:[],
        page_size:5,
        current_page:1,



    }
},

created(){
    this.req()

},

  methods: {
    req() {
      axios.get("money/lost_count").then(res => {
        this.total = res.data;
      })

      axios.get('money/lost', {
        params: {
          num: this.page_size,
          page: this.current_page,
        }
      }).then(res => {
        this.tableData = res.data
      })


    },

    handleSizeChange(e) {
      this.page_size = e
      this.req()

    },

    handleCurrentChange(e) {
      this.current_page = e
      this.req()
    },


    no_ban(id) {
      axios.post("money/ban?id=" + id + "&is_ban=0").then(res => {
        this.req();

        this.$message({
          message: '解除挂失成功',
          type: 'success'

        });

      })
    }
  }





}
</script>
