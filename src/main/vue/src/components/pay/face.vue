<template>
    <div>
      <div class="body">
        <h2>人脸支付系统</h2>


        <div class="bigbox">
          <div class="box">
            <div style="display: inline-block;margin-right: 30px">
              <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay ></video>
              <canvas style="display:none;" id="canvasCamera" :width="videoWidth" :height="videoHeight" ></canvas>
              <div v-if="imgSrc" class="img_bg_camera">

              </div>


            </div>
            <div style="display: inline-block">
              <el-form>
                <el-form-item label="学号">
                  <el-input v-model="student.id" autocomplete="off" disabled></el-input>
                </el-form-item>
              </el-form>

              <el-form>
                <el-form-item label="姓名">
                  <el-input v-model="student.name" autocomplete="off" disabled></el-input>
                </el-form-item>
              </el-form>


              <el-form>
                <el-form-item label="班级">
                  <el-input v-model="student.classes" autocomplete="off" disabled></el-input>
                </el-form-item>
              </el-form>

            </div>

            <div class="msg">
            <div v-if="money == ''">欢迎使用支付系统</div>
            <div v-if="money != ''">请支付{{money}}元</div>
            </div>

            <el-button @click="getCompetence" type="success" class="button">人脸支付</el-button>



          </div>
        </div>



      </div>









  </div>







</template>

<script>
import axios from 'axios'

export default{
    data(){
        return{
            money:"",
            videoWidth: 300,
            videoHeight: 300,
            imgSrc: '',
            thisCancas: null,
            thisContext: null,
            a:'',
            id:'',
            name:'',
            msg:'',
            bill_id:'',
            student:{
            id:"",
            classes:"",
            name:""
           }

        }
    },
     created() {

       this.initWebSocket()



    },
    destroyed() {
      this.websock.close();//离开路由之后断开websocket连接
    },
    methods: {
      initWebSocket(){//初始化weosocket(必须)
        const wsuri = "ws://localhost:8088/websocket";    //请根据实际项目需要进行修改
        this.websock = new WebSocket(wsuri);      //新建一个webstock对象
        this.websock.onmessage = this.websocketonmessage;
        this.websock.onopen = this.websocketonopen;
        this.websock.onerror = this.websocketonerror;
        this.websock.onclose = this.websocketclose;
        console.log(this.websock);
      },
      websocketonopen(){//websocket连接后发送数据(send发送)

      },
      websocketonerror(){//连接建立失败重连
        this.initWebSocket();
      },

      websocketonmessage(e){ //数据接收
        var data = JSON.parse(e.data)
        console.log(data.msg)

        if(data.msg === "支付请求"){
          console.log("支付请求get")

          this.money = data.money;
          this.bill_id = data.id;}

      },
      websocketsend(Data){//数据发送
        this.websock.send(Data);
      },
      websocketclose(e){  //关闭
        console.log('断开连接',e);
      },

      getCompetence () {
      var _this = this
      this.thisCancas = document.getElementById('canvasCamera')
      this.thisContext = this.thisCancas.getContext('2d')
      this.thisVideo = document.getElementById('videoCamera')
      // 旧版本浏览器可能根本不支持mediaDevices，我们首先设置一个空对象
      if (navigator.mediaDevices === undefined) {
        navigator.mediaDevices = {}
      }
      // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
      // 使用getUserMedia，因为它会覆盖现有的属性。
      // 这里，如果缺少getUserMedia属性，就添加它。
      if (navigator.mediaDevices.getUserMedia === undefined) {
        navigator.mediaDevices.getUserMedia = function (constraints) {
          // 首先获取现存的getUserMedia(如果存在)
          var getUserMedia = navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.getUserMedia
          // 有些浏览器不支持，会返回错误信息
          // 保持接口一致
          if (!getUserMedia) {
            return Promise.reject(new Error('getUserMedia is not implemented in this browser'))
          }
          // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
          return new Promise(function (resolve, reject) {
            getUserMedia.call(navigator, constraints, resolve, reject)
          })
        }
      }
      var constraints = { audio: false, video: { width: this.videoWidth, height: this.videoHeight, transform: 'scaleX(-1)' } }
      navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
        // 旧的浏览器可能没有srcObject
        if ('srcObject' in _this.thisVideo) {
          _this.thisVideo.srcObject = stream
        } else {
          // 避免在新的浏览器中使用它，因为它正在被弃用。
          _this.thisVideo.src = window.URL.createObjectURL(stream)
        }
        _this.thisVideo.onloadedmetadata = function (e) {
          _this.thisVideo.play()
        }
      }).catch(err => {
        console.log(err)
      })

        this.$message.success("开始检测人脸")

        this.block = 1;
        this.face = setInterval(async ()=>{
          await this.setImage()
        },500);


      },




	 dataURItoBlob (base64Data) {
	     var byteString;
	     if (base64Data.split(',')[0].indexOf('base64') >= 0)
	         byteString = atob(base64Data.split(',')[1]);
	     else
	         byteString = unescape(base64Data.split(',')[1]);
	     var mimeString = base64Data.split(',')[0].split(':')[1].split(';')[0];
	     var ia = new Uint8Array(byteString.length);
	     for (var i = 0; i < byteString.length; i++) {
	         ia[i] = byteString.charCodeAt(i);
	     }
	     return new Blob([ia], {type: mimeString});
	 },


    setImage () {
      this.block = this.block - 1

      //如果被进程占用，直接返回
      if(this.block < 0) {
        console.log("我被阻塞了")
        this.block = this.block + 1;
        return
      }

      console.log("我没被阻塞")
      var _this = this
      // 点击，canvas画图
      _this.thisContext.drawImage(_this.thisVideo, 0, 0, _this.videoWidth, _this.videoHeight)
      // 获取图片base64链接
      var image = this.thisCancas.toDataURL('image/png')
      console.log(image);
      _this.imgSrc = image
      this.$emit('refreshDataList', this.imgSrc)
      var a = this.dataURLtoFile(image,"hui.jpg")
      console.log(a)
      let formdata = new FormData()
      formdata.append("img",a);
      this.a = a;
      console.log(formdata)


      //上传图片到图像识别
      axios.post("http://127.0.0.1:2020/face",
        formdata,{
        headers: {
			    "Content-Type": "multipart/form-data"
				         }}
      ).then((responds) =>{

        console.log(responds.data)
        this.id = responds.data

        if(responds.data === "Unknown"){
          this.msg = "不在人脸库中"
          this.$message.error("不在人脸库中")
          this.block = this.block + 1;
          return
        }

        else if(responds.data === "no_face"){
          this.msg = "检测不到人脸"
          this.$message.error("检测不到人脸")
          this.block = this.block + 1;
          return
        }

        axios.get("student/find_one/" + this.id).then(res=>{

          this.student = res.data
        })


        //通过id识别余额和信息
        axios.get("money/find/" + this.id).then((res)=>{
          this.name = res.data.student.name;
          this.user_money = res.data.money;
          if(this.money > this.user_money){
            this.msg = "余额不足，支付失败!" + " 剩余余额 " + res.data.money;
            clearInterval(this.face);
          }
          else{
            let param = new URLSearchParams();
            param.append("money",this.money);
            param.append("id",this.id);
            //通过id识别
            axios.post("money/pay",param).then((res)=>{
              if(res.data === 1){
                this.msg = "本次消费" + this.money + "元" + " 剩余余额 " + (this.user_money - this.money)
                setTimeout(() =>{
                  this.$message.success(this.name + ",识别成功")
                })

                setTimeout(() =>{
                  this.$message.success(this.msg)
                })


                clearInterval(this.face);
            //更改状态
                /*
                let params = new URLSearchParams();
                params.append("id",this.bill_id);
                params.append("name",this.name);
                axios.post("/food/payed",params).then((res)=>{
                  console.log(res);
                })
                */

            //向商家端发送已经支付完成
                /*
                let parames = new URLSearchParams();
                parames.append("id",this.bill_id);
                axios.post("/pay/pay_over",parames).then((res)=>{
                  console.log(res)
                }) */

            //记录账单
                var date = new Date();
                var date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes()
                let parames = new URLSearchParams();
                parames.append("id",this.id);
                parames.append("money",this.money);
                parames.append("date",date);
                parames.append("content","消费");

                axios.post("/money/cost_stu",parames).then(res=>{
                  console.log(res)
                })


                axios.post("/food/bill_money?money=" + this.money).then(res=>{
                  console.log(res)
                })

            //支付金额清空
                this.money = ''

              }
              else{
                this.msg = "支付失败请重试";
                this.block = this.block + 1;
              }
            })



          }
        })
      })

    },



    // base64转文件
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
    // 关闭摄像头
    stopNavigator () {
      this.thisVideo.srcObject.getTracks()[0].stop()
    },

    },




    }




</script>


<style>

h2{
  text-align: center;
}

.box{
  margin:0px auto
}



.bigbox{
  width: 100%;
  height: 100vh;
  display: flex;
}

.el-input.is-disabled /deep/ .el-input__inner {
  color: #606266;
}

.button{
  width:100%;


}

.msg{
  margin-bottom: 20px;
}
</style>
