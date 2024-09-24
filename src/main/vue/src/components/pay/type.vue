<template>



  <div>


    <el-button @click="add">添加分类</el-button>

    <!-- 表格 -->
    <el-table
        :data="tableData"
        style="width: 100%"
        :tree-props="{children: 'food'}"
        row-key="id">



      <el-table-column
          label="分类"
          width="100"
          prop="type">
      </el-table-column>


      <el-table-column
          prop="imgSrc"
          label="图片"
          width="180">
        <template slot-scope="scope">
          <img :src="'http://localhost:8088/download?name=' + scope.row.imgSrc" v-if="scope.row.imgSrc !== undefined">
        </template>
      </el-table-column>
      <el-table-column
          prop="name"
          label="菜名"
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

      <el-table-column
          label="操作"
          width="300"

      >
        <template slot-scope="scope">
          <div v-if="scope.row.food">
            <el-button type="primary" @click="change(scope.row)">修改</el-button>
            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
          </div>

          <div v-else>
            <!--
           <el-button type="primary" @click="change_food(scope.row)">修改</el-button>
           <el-button v-if="scope.row.ban === 1" type="success" @click="ban_food(scope.row,0)">上架</el-button>
          <el-button v-if="scope.row.ban === 0" type="warning" @click="ban_food(scope.row,1)">下架</el-button>
            <el-button type="danger" @click="del_food(scope.row.id)">删除</el-button>
            -->
          </div>
        </template>

      </el-table-column>




    </el-table>

    <el-dialog title="新增分类" :visible.sync="addVisible" width="30%">
     <el-form>
      <el-form-item label="分类">
      <el-input v-model="foodType.type"></el-input>
      </el-form-item>
     </el-form>

      <el-button type="danger" style="width: 45%" @click="addVisible = false">取消</el-button>
      <el-button type="success" style="width: 45%" @click="adds">确认</el-button>


    </el-dialog>

    <el-dialog title="更改分类" :visible.sync="changeVisible" width="30%">
      <el-form>
        <el-form-item label="分类">
          <el-input v-model="foodType.type"></el-input>
        </el-form-item>
      </el-form>

      <el-button type="danger" style="width: 45%" @click="changeVisible = false">取消</el-button>
      <el-button type="success" style="width: 45%" @click="changes()">确认</el-button>


    </el-dialog>

    <!-- 修改菜品提示框 -->
    <el-dialog title="修改菜品" :visible.sync="foodChangeVisible">
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
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="food.imgSrc" :src="'http://localhost:8088/download?name=' + food.imgSrc" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item label="菜品介绍">
          <el-input v-model="food.content" autocomplete="off"></el-input>
        </el-form-item>



      </el-form>

      <el-button @click="dialogFormChangeVisible = false">取 消</el-button>
      <el-button type="primary" @click="new_change()">确 定</el-button>
    </el-dialog>


  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "type",
  data(){
    return{
      tableData:"",
      addVisible:false,
      changeVisible:false,
      foodChangeVisible:false,
      foodType:{
        type:"",
      },
      food:{},
      options:{},
    }
  },
  created(){
    this.req();
  },

  methods:{

    add(){
     this.addVisible = true;
     this.foodType = {};
    },


    //新增分类
    adds(){
      axios.post("type/add",this.foodType).then(res=>{
        if(res.data === 1){
          this.$message.success("添加成功")
          this.addVisible = false
          this.req()
        }
        else{
          this.$message.error("添加失败")
        }

      }).catch(err=>{
        this.$message.error("服务器出问题了，请稍后再试")
      })
    },

    //刷新
    req(){
      axios.get("type/get_all").then(res=>{
        this.tableData = res.data
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

    //删除分类
    del(id){
      this.$confirm("你确定要删除吗").then(con =>{
        axios.post("type/del/" + id).then(res=>{
          if(res.data === 1){
            this.$message.success("删除成功")
            this.req()
          }
          else this.$message.error("删除失败")
        }).catch(err=>{
          this.$message.error("服务器崩了,请稍后再试")
        })

      })

    },

    change(score){
      this.foodType = score
      this.foodType.old_type = score.type
      this.changeVisible = true


    },

    changes(){
      axios.post("type/change",this.foodType).then(res=>{
        if(res.data === 1) {
          this.$message.success("更改成功");
          this.req();

        }
        else{
          this.$message.error("更改失败")
        }
      }).catch(err=>{
        this.$message.error("服务器崩了")
      });

      axios.post("type/changes?old="+this.foodType.old_type + "&new=" + this.foodType.type).then(res=>{
        if(res.data === 1) {
          this.$message.success("更改成功");
          this.req();
          this.changeVisible = false;
        }
        else{
          this.$message.error("更改失败")
        }
      }).catch(err=>{
        this.$message.error("服务器崩了")
      })
    },
    },



    //删除食品
    del_food(id) {
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
      this.req();


    },


    ban_food(e,is_ban){
      this.food = e
      this.food.ban = is_ban
      axios.post("food/new_change",this.food).then(res=>{
        if(res.data === 1) {
          this.$message.success("修改成功")
          this.req();
        }
      })
    },

    change_food(item) {
      this.food = item;
      this.foodChangeVisible = true;
    },




    handleAvatarSuccess(res, file) {
      //this.student.img = file
      this.food.imgSrc = "food/" + res + ".jpg"
      this.$forceUpdate()
      console.log(this.food.imgSrc)

    },







}
</script>

<style scoped>
img{
  height:60px;
  width: 90px;
}

.el-table{

  font-size: 15px;
  color:black;
}
</style>
