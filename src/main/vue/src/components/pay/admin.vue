<template>
<div>

  <!-- 搜索框 -->
  <div>
    <!--输入栏 -->
    <el-input v-model="name" placeholder="请输入搜索菜品内容" style="width: 30%"></el-input>
    <!-- 提交按钮 -->
    <el-button type="primary" @click="search()">提交</el-button>
  </div>


  <el-button type="primary" @click="dialogFormaddVisible = true;food = {}">添加菜品</el-button>
  <!--
  <el-button type="danger" @click="multi_del()">批量删除</el-button>
  -->
  <!-- 表格 -->

  <el-table
      :data="tableData"
      style="width: 100%">
    <!--
    <el-table-column
        label="选择"
        width="80">
      <template slot-scope="scope">
      <el-checkbox @change="add_del(scope.row.id)"></el-checkbox>
      </template>
    </el-table-column>
    -->
    <el-table-column
        prop="imgSrc"
        label="图片"
        width="180">
      <template slot-scope="scope">
        <img :src="'http://localhost:8088/download?name=' + scope.row.imgSrc">
      </template>
    </el-table-column>
    <el-table-column
        prop="name"
        label="菜名"
        width="180">
    </el-table-column>
    <el-table-column
        prop="type"
        label="类别"
        width="180">
    </el-table-column>
    <el-table-column
        prop="money"
        label="价格"
        width="180">
    </el-table-column>
    <!--
    <el-table-column
        prop="ban"
        label="是否下架"
        width="180">
    </el-table-column>
    -->




    <el-table-column label="操作" width="200">
      <template slot-scope="scope">
        <el-button type="primary" @click="change(scope.row)">修改</el-button>
        <el-button v-if="scope.row.ban === 1" type="success" @click="ban(scope.row,0)">上架</el-button>
        <!--<el-button v-if="scope.row.ban === 0" type="warning" @click="ban(scope.row,1)">下架</el-button> -->
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



  <!-- 添加菜品提示框 -->
  <el-dialog title="添加菜品" :visible.sync="dialogFormaddVisible">

    <el-form>


      <el-form-item label="菜品名">
        <el-input v-model="food.name" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="价格">
        <el-input v-model="food.money" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="分类">
        <!--选择栏 -->
        <el-select v-model="food.type" placeholder="请选择" style="">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="照片">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8088/img?type=food"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="food.imgSrc" :src="'http://localhost:8088/download?name=' + food.imgSrc" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="菜品介绍">
        <el-input v-model="food.content" autocomplete="off" type="textarea"></el-input>
      </el-form-item>

    </el-form>

    <el-button @click="dialogFormaddVisible = false">取 消</el-button>
    <el-button type="primary" @click="adds()">确 定</el-button>
  </el-dialog>

  <!-- 修改菜品提示框 -->
  <el-dialog title="修改菜品" :visible.sync="dialogFormChangeVisible">
    <el-form>


      <el-form-item label="菜品名">
        <el-input v-model="food.name" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="价格">
        <el-input v-model="food.money" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="分类">
        <!--选择栏 -->
        <el-select v-model="food.type" placeholder="请选择" style="">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="照片">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:8088/img?type=food"
            :show-file-list="false"
            :on-success="handleAvatarSuccess">
          <img v-if="food.imgSrc" :src="'http://localhost:8088/download?name=' + food.imgSrc" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="菜品介绍">
        <el-input v-model="food.content" autocomplete="off" type="textarea"></el-input>
      </el-form-item>



    </el-form>

    <el-button @click="dialogFormChangeVisible = false">取 消</el-button>
    <el-button type="primary" @click="new_change()">确 定</el-button>
  </el-dialog>





</div>
</template>

<script>
import axios from 'axios';
export default {
  data(){
    return{
      tableData:[],
      dialogFormaddVisible:false,
      dialogFormChangeVisible:false,
      food: {},
      img:"",
      name:"",

      current_page:1,
      page_size:5,
      total:"",
      del_list:[],
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






    }
  },

  created() {
     this.menu();

  },

  methods:{
    menu(){
      axios.get("food/menu_page",{params:{
        name:this.name,
        page:this.current_page,
        size:this.page_size,
      }}).then((res)=>{
        console.log(res.data)
        console.log(res.data.records)

        this.tableData = res.data.records
        this.total = res.data.total


      })

      axios.get("type/get_all").then(res=>{
        this.options = [];
        for(var i=0;i<res.data.length;i++){
          console.log(res.data[i].type)
          this.options.push({value:res.data[i].type,label:res.data[i].type});
          console.log(this.options)
        }
      })
    },
    adds(){

        axios.post("food/new_add",this.food).then((res)=>{
          console.log(res)
          if(res.data === 1){
            this.$message.success("添加成功")
             this.dialogFormaddVisible = false;
              this.menu();

          }
        })


      //添加菜品


    },

    //上传图片
    httpRequest(item){
      console.log(item.file)
      this.food.img = item.file;

    },

    change(item) {
      console.log(item);
      //this.food = item;

        this.food = JSON.parse(JSON.stringify(item));
      this.dialogFormChangeVisible = true;
    },

    new_change() {

      axios.post("food/new_change",this.food).then(res=>{
        if(res.data === 1) {
          this.$message.success("修改成功");
          this.dialogFormChangeVisible = false;
          this.menu()

        }
      })

    },





    //删除
    del(id) {
      console.log(id);

      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //执行删除
        axios.post("food/del/" + id).then(res => {
          console.log(res);
          this.$message({
            message: '恭喜你，删除成功',
            type: 'success'
          });
          this.menu();

        }).catch(err => {
          this.$message({
            message: '删除失败',
            type: 'danger'
          });
        })
      }).catch(() => {

      });


      //刷新
      this.menu();


    },


    //查找
    search(){
      this.menu();
      this.current_page = 1

    },

    handleSizeChange(e){
      this.page_size = e
      this.menu()


    },


    handleCurrentChange(e){
      this.current_page = e
      this.menu()

    },

    add_del(id){
      //当在删除队列不存在
      if(this.del_list.indexOf(id) === -1){
        this.del_list.push(id);
      }
      else{
        this.del_list.splice(this.del_list.indexOf(id),1)
      }
       console.log(this.del_list)
    },


    multi_del(){
      //var id = this.del_list.map(v=>v.id)
      axios.post("food/dels",this.del_list).then(res=>{
        console.log(res)
      if(res.data !== "") this.$message.success("批量删除成功")
        else this.$message.error("批量删除成功")
        this.current_page = 1

        this.menu();
      })
    },

    ban(e,is_ban){
      this.food = e
      this.food.ban = is_ban
      axios.post("food/new_change",this.food).then(res=>{
        if(res.data === 1) {
          this.$message.success("修改成功")
          this.menu();
        }
      })
    },

    handleAvatarSuccess(res, file) {
      this.food.imgSrc = "food/" + res + ".jpg"
      this.food.img = file
      this.$forceUpdate()
      console.log(this.food)
      console.log(this.food.imgSrc)

    },



  },



}
</script>

<style scoped>
img{
  height:60px;
  width: 90px;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}

.el-table{

  font-size: 20px;
  color:black;
}

thead{
  color:black;
}
</style>
