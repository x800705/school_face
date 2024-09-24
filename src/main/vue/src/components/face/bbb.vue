<template>

</template>



<script>

import mqtt from 'mqtt'

export default {
  name: "bbb",
  data(){
    return{

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
      },

    }

  },

  created(){
    //console.log(mqtt)
    this.mqttConf();
  },

  methods:{
    mqttConf() {
      // 链接mqtt
      this.ledDevice.client = mqtt.connect(this.ledDevice.options.url, this.ledDevice.options);
      // 链接成功回调方法

      let that = this
      this.ledDevice.client.on('connect', function()   {

         that.$message.success('连接成功!');
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
  this.ledDevice.client.publish(this.ledDevice.options.topic, status.toString(), {
    qos: 1
  });
},
  }
}
</script>

<style scoped>

</style>
