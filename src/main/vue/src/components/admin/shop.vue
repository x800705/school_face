<template>
  <div>
    <h3>食堂管理人员管理</h3>

    <el-button @click="dialogFormVisible = true">添加食堂管理人员</el-button>

    <!-- 表格 -->
    <el-table
        :data="tableData"
        stripe
        style="">
      <el-table-column
          prop="id"
          label="商家号"
          width="250">
      </el-table-column>
      <el-table-column
          prop="name"
          label="姓名"
          width="180">
      </el-table-column>




      <el-table-column label="操作" width="360">
        <template slot-scope="scope">
          <el-button type="danger" @click="del(scope.row)">删除</el-button>
          <el-button type="info" @click="password(scope.row)">重置密码</el-button>
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


    <!-- 添加学生提示框 -->

    <el-dialog title="添加食堂管理人员" :visible.sync="dialogFormVisible">
      <el-form>



        <el-form-item label="商家号">
          <el-input v-model="business.id" autocomplete="off"></el-input>
        </el-form-item>



        <el-form-item label="姓名">
          <el-input v-model="business.name" autocomplete="off"></el-input>
        </el-form-item>




      </el-form>

      <el-button @click="dialogFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="add()" :loading="loading">确 定</el-button>
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
      value: 'id', // 选择内容
      content: '', // 输入内容
      current_page:1, // 当前页数
      page_size:5, // 页面显示条数
      total:0, // 总数

      student:{
        img:"",
        imgSrc:"",
        id:"",
        name:"",
        pwd:"",
        classes:"",
      },
      dialogFormVisible:false,
      dialogFormchangeVisible:false,
      is_post:false,
      iconBase64:"",
      loading:false,
      business:{
        id:"",
        name:"",
      }
    }
  },

  created(){
    this.req()
  },
  methods: {
    change(e) {
      this.dialogFormchangeVisible = true;
      this.student = e;
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
      console.log(this.student.imgSrc)


      //数据放到数据库中
      axios.post("student/new_changes",this.student).then(res=>{
        if(res.data === 1) {
          this.$message.success("修改成功")
          console.log(this.student.img)
          if(this.student.img === undefined || this.student.img === ""){
            this.dialogFormchangeVisible = false
            this.loading = false
          }
          else{
            let mf = new FormData();
            mf.append('img', this.student.img.raw);
            mf.append('id', this.student.id);

            //更新人脸库
            axios.post("http://localhost:2020/change_face",mf, {
              headers: {
                'Content-Type': 'multipart/form-data'
              }
            }).then(res=>{
              console.log(res)
              this.$message.success("人脸库更新成功")
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
        this.$message.error("数据更改失败")
        this.loading = false
      })
    },


    req() {
      let param = new URLSearchParams();
      param.append("page",this.current_page);
      param.append("size",this.page_size);
      axios.post("business/page",param).then(res=>{
        this.tableData = res.data.records
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

    del(row) {
      this.$confirm('删除商家是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        axios.post('business/del',row).then((res) => {
          this.$message.success("删除成功")
        })
        this.req()
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
      //获取数据加入数据库
      this.loading = true
      let param = new URLSearchParams();
      param.append("id", this.business.id);
      param.append("name", this.business.name);

      //加入到学生数据库
      axios.post('business/add', param).then(res => {
        this.$message.success("添加成功")
        this.req()


      }).catch(err => {

        console.log(err)
        this.$message.error('添加失败');
        this.loading = false
      })





    },

    adds() {
      this.student = {
        id: "",
        name: "",
        pwd: "",
        classes: "",
        img:"",
        imgSrc:""
      },

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
      this.student.img = file
      this.student.imgSrc = "student/" + res + ".jpg"
      console.log(this.student.imgSrc)

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


    password(e){

      this.$confirm('重置密码是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {

        this.business = e
        this.business.pwd = "123456"

        axios.post("business/reset",this.business).then(res=>{
          console.log(res.data)
          this.$message.success("重置密码为123456")
        })
        this.req()
      })

    }



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
  font-size: 23px;
  color: black;
  font-family: 微软雅黑;
}
.el-table__header-wrapper .cell{
  color:black;
  font-weight: bold;
}
</style>
