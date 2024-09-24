<template>
  <div>
    <div class="camera_outer">
      <div style="width: 500px;height:600px;display:inline-block">
      <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay></video>
      <canvas style="display:none;" id="canvasCamera" :width="videoWidth" :height="videoHeight" ></canvas>
      <div v-if="imgSrc" class="img_bg_camera">
      </div>
        <div>
          <el-button @click="getCompetence()">打开摄像头</el-button>
          <el-button @click="stopNavigator()">关闭摄像头</el-button>
        </div>


      </div>


      <div style="display:inline-block;">
        <div v-if="msg === ''">获取课堂状态</div>
        <div v-if="msg === 'no_face'">没有检测到人脸</div>
        <div v-else>
          生气:{{msg.angry}} <br>
          恶心:{{msg.disgusted}} <br>
          害怕:{{msg.fearful}} <br>
          开心:{{msg.happy}} <br>
          中立:{{msg.neutral}} <br>
          伤心:{{msg.sad}} <br>
          惊讶:{{msg.surprised}}<br>

        </div>
      </div>

    </div>
  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "come",
  data(){
    return{
      face:"",
      money:"",
      videoWidth: 300,
      videoHeight: 600,
      imgSrc: '',
      thisCancas: null,
      thisContext: null,
      class_id:'',
      a:'',
      id:'',
      name:'',
      msg:'',
      bill_id:'',
      ledDevice: {
        descText: 'LED - 智能灯',
        img: ['img/OFF.png', 'img/ON.png'],
        color: ['#E9E9E9', '#19BF6C'],
        descTextStyle: {
          color: '#E9E9E9'
        },
        status: 0,
        client: null,
      },

    }
  },

  created() {
    this.class_id = this.$route.params.id

  },

  methods:{

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

      this.$message.success("开始检测人脸表情")


      this.face = setInterval(async ()=>{
        await this.setImage()
      },1000);


    },

    // 关闭摄像头
    stopNavigator () {
      this.thisVideo.srcObject.getTracks()[0].stop()
    },

    //表情检测
    setImage () {
      var _this = this
      // 点击，canvas画图
      _this.thisContext.drawImage(_this.thisVideo, 0, 0, _this.videoWidth, _this.videoHeight)
      // 获取图片base64链接
      var image = this.thisCancas.toDataURL('image/png')
      //console.log(image);
      _this.imgSrc = image
      this.$emit('refreshDataList', this.imgSrc)
      var a = this.dataURLtoFile(image,"hui.jpg")
      //console.log(a)
      let formdata = new FormData()
      formdata.append("img",a);
      this.a = a;
      //console.log(formdata)


      //上传图片到图像识别
      axios.post("http://127.0.0.1:2020/face_express",
          formdata,{
            headers: {
              "Content-Type": "multipart/form-data"
            }}
      ).then((responds) =>{


        this.msg = responds.data
        console.log(this.msg)

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






  }
}
</script>

<style scoped>

</style>
