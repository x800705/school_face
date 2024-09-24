<template>
  <div>
    <div>
      <h3>教师管理</h3>
      <!-- 搜索框 -->
      <div>
        <!--选择栏 -->
        <el-select v-model="value" placeholder="请选择" style="width: 10%">
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




      </div>

      <el-button type="primary" @click="adds()"><i class="el-icon-plus"></i>添加老师</el-button>

    </div>



     <!-- 表格 -->
    <el-table
        :data="tableData"
        style="width: 100%;color:black;font-size:20px;font-family:微软雅黑">
      <el-table-column
          prop="id"
          label="教工号"
          width="250">
      </el-table-column>
      <el-table-column
          prop="imgSrc"
          label="照片"
          width="180">
        <template slot-scope="scope">
          <img :src="'http://localhost:8088/download?name=' + scope.row.imgSrc">
        </template>
      </el-table-column>

      <el-table-column
          prop="name"
          label="姓名"
          width="180">
      </el-table-column>

      <el-table-column
          prop="classes"
          label="管理班级"
          width="180">
      </el-table-column>

      <el-table-column
          prop="phone"
          label="手机号"
          width="180">
      </el-table-column>



      <el-table-column label="操作" width="320">
        <template slot-scope="scope">
          <el-button type="primary" @click="change(scope.row)">修改</el-button>
          <el-button type="danger" @click="del(scope.row)">删除</el-button>
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



    <!-- 添加老师提示框 -->

    <el-dialog title="添加教师" :visible.sync="dialogFormVisible">
      <el-form>
        <!--
             <el-form-item label="图片">
               <el-upload
                   class="upload-demo"
                   :http-request="upload_img"
                   multiple
                   :limit="3">
                 <el-button size="small" type="primary">点击上传</el-button>
               </el-upload>
             </el-form-item>
             -->

        <el-form-item label="照片">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8088/img?type=teacher"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">

            <img v-if="teacher.imgSrc" :src="'http://localhost:8088/download?name=' + teacher.imgSrc" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="教工号">
          <el-input v-model="teacher.id" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="姓名">
          <el-input v-model="teacher.name" autocomplete="off"></el-input>
        </el-form-item>



        <el-form-item label="管理班级">
          <el-input v-model="teacher.classes" autocomplete="off"></el-input>
        </el-form-item>


        <div>密码默认为123456</div>

      </el-form>

      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="add()" :loading="loading">确 定</el-button>
    </el-dialog>

    <el-dialog title="更改教师信息" :visible.sync="dialogFormchangeVisible">
      {{teacher}}
      <el-form>
        <el-form-item label="学号">
          <el-input v-model="teacher.id" autocomplete="off" disabled></el-input>
        </el-form-item>

        <el-form-item label="姓名">
          <el-input v-model="teacher.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="管理班级">
          <el-input v-model="teacher.classes" autocomplete="off"></el-input>
        </el-form-item>



        <el-form-item label="照片">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8088/img?type=teacher"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="teacher.imgSrc" :src="'http://localhost:8088/download?name=' + teacher.imgSrc" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>


      </el-form>

      <el-button @click="dialogFormchangeVisible = false">取 消</el-button>
      <el-button type="primary" @click="new_change()" :loading="loading">确 定</el-button>
    </el-dialog>



  </div>









</template>

<script>
import search from '../component/search.vue'
import axios from 'axios'
export default{
  components:{
    search
  },
  data(){
    return{
      tableData:[],
      options: [{
        value: 'id',
        label: '教工号'
      }, {
        value: 'name',
        label: '姓名'
      }, {
        value: 'classes',
        label: '管理班级'
      }],
      value: 'id', // 选择内容
      content: '', // 输入内容
      current_page:1, // 当前页数
      page_size:5, // 页面显示条数
      total:0, // 总数

      teacher:{},
      dialogFormVisible:false,
      dialogFormchangeVisible:false,
      is_post:false,
      iconBase64:"",
      loading:false,
    }
  },

  created(){
    this.req()
  },
  methods: {
    change(e) {
      this.dialogFormchangeVisible = true;
      this.teacher = e;
      console.log(e)
    },
    changes(e) {

      let mf = new FormData();
      mf.append('photo', this.student.img);
      mf.append('type', "student");

      //上传图片到服务器，获取图片路径
      axios.post("img", mf, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      }).then((res) => {
        this.student.imgSrc = "student/" + res.data + ".jpg"
        console.log(this.student.imgSrc)
        axios.post('student/new_changes', this.student).then(res => {


          this.$message({
            message: '更改成功',
            type: 'success'

          });
          this.dialogFormchangeVisible = false


        }).catch(err => {

          this.$message.error('更改失败');
        })

      })
    },

    new_change(){
      this.loading = true



      //数据放到数据库中
      axios.post("teacher/update",this.teacher).then(res=>{
        if(res.data === 1) {

          setTimeout(() =>{
            this.$message.success("修改成功")


          },10)




          console.log(this.teacher.img)

          if(this.teacher.img !== undefined){
            let mf = new FormData();
            mf.append('img', this.teacher.img.raw);
            mf.append('id', this.teacher.id);

            //更新人脸库
            axios.post("http://localhost:2020/change_face",mf, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }).then(res=>{
              console.log(res)
              setTimeout(() =>{
                this.$message.success("人脸库更新成功")
              },10)
              this.dialogFormchangeVisible = false
              this.loading = false
            }).catch(err =>{
              this.$message.error("人脸库更新失败")
              this.loading = false
            })

          }





          this.req()
        }
      }).catch(err =>{
        this.$message.error(err)
        this.loading = false
      })
    },


    req() {

      axios.get("teacher/get_all",{
        params:{

          page:this.current_page,
          size:this.page_size,
        }
      }).then(res=>{
         this.tableData = res.data.records;
         this.total = res.data.total;
      })

    },

    search() {
      this.req()
    },
    handleSizeChange(val) {
      this['page_size'] = val
      this.req()
    },
    handleCurrentChange(val) {
      this['current_page'] = val
      this.req()
    },

    del(teacher) {
      this.$confirm('删除教师是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //删除教师数据库
        axios.post('teacher/delete',teacher).then(async res => {
          if (res.data === 1)
            setTimeout(() =>{
              this.$message.success("删除成功")
            },10)



            //删除人脸库
            axios.post("http://localhost:2020/delete_face?id=" + teacher.id).then(async res => {
              console.log(res.data)
              if (res.data === 200) {
                setTimeout(() =>{
                  this.$message.success("人脸库删除成功")
                  this.req()
                },10)
              }
              else this.$message.error("删除失败")
            })

        }).catch(err=>{
          this.$message.error(err)

        })

      })
    },


    add() {
      /*
      let mf = new FormData();
      mf.append('photo', this.student.img);
      mf.append('type', "student");

      //上传图片到服务器，获取图片路径
      axios.post("img", mf, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }

       */



      this.teacher.pwd = '123456';
      this.$forceUpdate()
      //获取数据加入数据库
      this.loading = true

      if(this.teacher.id[0] !== 'S'){
        this.$message.error("教师号开头需要以大写S开头")
        this.loading = false
        return
      }

      /*
      let param = new URLSearchParams();

      param.append("imgSrc", this.student.imgSrc);
      param.append("id", this.student.id);
      param.append("name", this.student.name);
      param.append("classes", this.student.classes);
      param.append("pwd", this.student.id.substring(this.student.id.length - 6, this.student.id.length));

       */


      //加入到教师数据库
      axios.post('teacher/add', this.teacher).then(async res => {
        await this.$message.success("添加成功")

        await this.$message.info("正在更新人脸库。。。")
        //更新人脸数据库
        let mf = new FormData();
        mf.append('img', this.teacher.img.raw);
        mf.append('id', this.teacher.id);

        axios.post("http://localhost:2020/add_face", mf, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          console.log(res)
          this.$message.success("人脸库更新成功")
          this.dialogFormVisible = false
          this.loading = false
          this.req()
        }).catch(res => {
          this.$message.error("人脸库更新失败,请在修改教师中重新上传人脸")
          this.loading = false
        })

      }).catch(err=>{
        this.$message.error("添加数据库失败")
        this.loading = false
      })



    },

    adds() {

          this.teacher = {},



          this.dialogFormVisible = true


    },



    upload_img(img) {
      this.student.img = img.file;
    },




    download(name){
      axios.get("download?name=" + name).then(res =>{
        this.student.img_src = res.data
      })
    },

    handleAvatarSuccess(res, file) {
      this.teacher.img = file
      this.$forceUpdate()
      this.teacher.imgSrc = "teacher/" + res + ".jpg"

    },

    beforeAvatarUpload(file) {

      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },


    getBase64(file) {
      return new Promise(function (resolve, reject) {
        const reader = new FileReader()
        let imgResult = ''
        reader.readAsDataURL(file)
        reader.onload = function () {
          imgResult = reader.result
        }
        reader.onerror = function (error) {
          reject(error)
        }
        reader.onloadend = function () {
          resolve(imgResult)
        }
      })
    },

    async imageToBase64 (file) {
      var reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = () => {
        console.log('file 转 base64结果：' + reader.result)
        this.iconBase64 = reader.result
      }



    },
    dataURLtoFile (dataurl, filename) {
      var arr = dataurl.split(',')
      var mime = arr[0].match(/:(.*?);/)[1]
      var bstr = atob(arr[1])
      var n = bstr.length
      var u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new File([u8arr], filename, { type: mime })
    },




  },






}

</script>

<style>

img {
  width: 60px;
  height: 80px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.avatar {
  width: 100px;
  height: 100px;
  display: block;
}

.cell{
  text-align: center;
}
.el-table__header-wrapper .cell{
  color:black;
  font-weight: bold;
}
</style>
