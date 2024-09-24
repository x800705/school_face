<template>
  <div>
    <div class="camera_outer">
      <div style="display: inline-block">
        <video id="videoCamera" :width="videoWidth" :height="videoHeight" autoplay ></video>
        <canvas style="display:none;" id="canvasCamera" :width="videoWidth" :height="videoHeight" ></canvas>
        <div v-if="imgSrc" class="img_bg_camera">

        </div>
        <el-button @click="getCompetence()">打开摄像头</el-button>
        <el-button @click="stopNavigator()">关闭摄像头</el-button>

      </div>


    </div>
  </div>
</template>

<script>
import axios from "axios"
import mqtt from "mqtt";
import setPromiseInterval, { clearPromiseInterval } from 'set-promise-interval'

export default {
  name: "come",
  data(){
    return{
      block:1,
      face:"",
      money:"",
      videoWidth: 300,
      videoHeight: 600,
      imgSrc: '',
      thisCancas: null,
      thisContext: null,
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
        options: {
          url: 'ws://test.ranye-iot.net:8080', //mqtt服务器
          // 需要修改
          topic: 'connect_all_esp8266_mqtt_face_topic',
          connectTimeout: 5000,
          clientId: 'connect_all_esp8266_mqtt_face_' + new Date().getTime(),
          clean: false,
          keepAliveInterval: 30
        },

        student:""
      },

    }
  },

  created() {
    this.mqttConf();
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

      this.$message.success("开始检测人脸")

      this.block = 1;
      this.face = setInterval(async ()=>{
        await this.setImage()
      },500);




      // this.face = setPromiseInterval(this.setImage(), 100)


    },

    // 关闭摄像头
    stopNavigator () {
      this.thisVideo.srcObject.getTracks()[0].stop()
    },



    //人脸检测和识别
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
      axios.post("http://127.0.0.1:2020/face",
          formdata,{
            headers: {
              "Content-Type": "multipart/form-data"
            }}
      ).then((responds) =>{

        console.log(responds.data)


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
        //识别成功 停止轮询
        else{
          clearInterval(this.face);
          //clearPromiseInterval(this.face)
          this.id = responds.data
          this.mqttPublish(1); //发送mqtt请求开门
          //进校

          axios.get("student/find_one/" + this.id).then(res=>{
            console.log(res.data)
            this.student = res.data

            this.student.state = 0


            axios.post("student/go",this.student).then(res=>{
              console.log(res)
            })

            this.$message.success(res.data.name +"! 欢迎回来")
          })



          //this.msg = "真的是你啊" + this.id
          //this.$message.success("真的是你啊" + this.id)





          this.block = this.block + 1;

        }






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

    //mqtt服务器的配置文件
    mqttConf() {
      // 链接mqtt
      this.ledDevice.client = mqtt.connect(this.ledDevice.options.url, this.ledDevice.options);
      // 链接成功回调方法

      let that = this
      this.ledDevice.client.on('connect', function()   {

        that.$message.success('连接硬件mqtt服务器成功!');
        that.ledDevice.client.subscribe(that.ledDevice.options.topic, function(err) {
          if (!err) {
            console.log('订阅成功:', that.ledDevice.options.topic)
          } else {
            console.log('订阅失败:', that.ledDevice.options.topic, err)
          }
        })
      }).on('reconnect', function(error) {
        that.$message.info('正在重连...');
      }).on('error', function(error) {
        that.$message.error('连接失败!');
      }).on('end', function() {
        that.$message.warn('连接断开!');
      }).on('message', function(topic, message) {
        // 接收到消息
        that.ledDevice.status = Number(message);
        that.ledDevice.descTextStyle = {
          color: that.ledDevice.color[Number(message)]
        };
      });
    },

    //mqtt发送信息
    mqttPublish(status) {
      console.log("已发送" + status)
      this.ledDevice.client.publish(this.ledDevice.options.topic, status.toString(), {
        qos: 1
      });
    },


  }
}
</script>

<style scoped>

</style>
