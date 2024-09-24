<template>
    <div>
    <h3>管理员管理</h3>
    <!--面包屑 -->


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



<el-button type="primary" @click="add()">添加管理员</el-button>
  </div>

       <!-- 表格 -->
     <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="管理员号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="180">
      </el-table-column>


         <el-table-column
             prop="pro"
             label="权限"
             width="180">
           <template slot-scope="scope">
           <el-tag v-if="scope.row.pro === '超级管理员'" type="warning" >
             {{scope.row.pro}}
           </el-tag>

             <el-tag v-else>
               {{scope.row.pro}}
             </el-tag>

           </template>
         </el-table-column>





      <el-table-column label="操作" width="360">
          <template slot-scope="scope">
              <el-button type="primary" @click="change(scope.row)">修改</el-button>
              <el-button type="danger" @click="del(scope.row.id)">删除</el-button>

          </template>
      </el-table-column>

    </el-table>

     <!-- 分页 -->
       <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="current_page"
      :page-sizes="[1,2,5]"
      :page-size="page_size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>


       <!-- 添加管理员提示框 -->
      <el-dialog title="添加管理员" :visible.sync="dialogFormaddVisible">
       <el-form>
    <el-form-item label="管理员编号">
      <el-input v-model="admin.id" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="姓名">
      <el-input v-model="admin.name" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="权限">
      <!--选择栏 -->
      <el-select v-model="admin.pro" placeholder="请选择" style="width: 15%">
        <el-option
            v-for="item in pro_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>




    密码默认编号后6位

    </el-form>

    <el-button @click="dialogFormaddVisible = false">取 消</el-button>
    <el-button type="primary" @click="adds()">确 定</el-button>
    </el-dialog>

        <!-- 更改管理员提示框 -->
      <el-dialog title="更改管理员" :visible.sync="dialogFormchangeVisible">
       <el-form>
    <el-form-item label="管理员编号">
      <el-input v-model="admin.id" autocomplete="off" disabled></el-input>
    </el-form-item>

    <el-form-item label="姓名">
      <el-input v-model="admin.name" autocomplete="off"></el-input>
    </el-form-item>

    <el-form-item label="权限">
      <!--选择栏 -->
      <el-select v-model="admin.pro" placeholder="请选择" style="width: 15%">
        <el-option
            v-for="item in pro_options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="密码">
      <el-input v-model="admin.pwd" autocomplete="off" type="password"></el-input>
    </el-form-item>



    </el-form>

    <el-button @click="dialogFormchangeVisible = false">取 消</el-button>
    <el-button type="primary" @click="changes()">确 定</el-button>
    </el-dialog>





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

       pro_options:[{
          value: '管理员',
          label: '管理员'
      }, {
        value: '超级管理员',
        label: '超级管理员'
  }],

        value: 'id',
        content: '',
        total:5,
        tableData:[],
        page_size:5,
        current_page:1,
        dialogFormchangeVisible:false,
        dialogFormaddVisible:false,
        admin:{
          id:"",
          name:"",
          pro:"",
          pwd:""
        }



    }
},

created(){
  this.req()
},

methods:{
req(){
  axios.get('admin/count').then((res)=>{
     this.total = res.data
   })

    axios.get("admin/search",{
        params:{
          num:this.page_size,
          page:this.current_page,
          content:this.content,
          methon:this.value,
        }
      }).then((responds) =>{
        console.log("123")
        console.log(responds);
        this.tableData = responds.data;
      })
},

handleSizeChange(e){
  this.page_size = e
  this.req()

},

handleCurrentChange(e){
  this.current_page = e
  this.req()
},
change(e){
this.dialogFormchangeVisible = true
this.admin = {
id:e.id,
name:e.name,
pro:e.pro,
pwd:e.pwd
}


},

changes(e){
       let param = new URLSearchParams();
       param.append("id",this.admin.id);
       param.append("name",this.admin.name);
       param.append("pro",this.admin.pro);
       param.append("pwd",this.admin.pwd);

         axios.post('admin/change',param).then(res =>{


                 this.$message({
                 message: '更改成功',
                 type: 'success'

        });
               this.dialogFormchangeVisible = false
               this.req();




         }).catch(err =>{

              this.$message.error('更改失败');
         })
     },



    del(id){
     this.$confirm('删除是否继续?', '删除无法撤回,请谨慎操作', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          axios.post('/admin/del/' + id).then((res) =>{

              console.log(res)
              this.req()
         })

        })
      },

      add(){
        this.dialogFormaddVisible = true;

        this.admin ={
          id:"",
          name:"",
          pro:"",
          pwd:""
        }
      },

      adds(){
       let param = new URLSearchParams();
       param.append("id",this.admin.id);
       param.append("name",this.admin.name);
       param.append("pro",this.admin.pro);
       param.append("pwd",this.admin.id.substring(this.admin.id.length-6,this.admin.id.length));

         axios.post('admin/add',param).then(res =>{


                 this.$message({
                 message: '添加成功',
                 type: 'success'

        });
               this.dialogFormaddVisible = false
               this.req()



         }).catch(err =>{

              this.$message.error('添加失败');
         })


    },


}





}
</script>
