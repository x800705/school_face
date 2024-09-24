<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>ESP8266-MQTT-LED</title>
  <link rel="stylesheet" type="text/css" href="css/iview.css">
  <link rel="stylesheet" type="text/css" href="css/mqtt.css">
</head>
<body>
<div id="app">
  <template>
    <div class="main">
      <div class="imgDiv" @click="switchFun">
        <img :src="ledDevice.img[ledDevice.status]" class="img" />
      </div>
      <div class="desc">
        <span class="descText" :style="[ledDevice.descTextStyle]">{{ ledDevice.descText }}</span>
      </div>
      <div class="bottomDesc">
        {{ bottomDesc }}
      </div>
    </div>
  </template>
</div>
<script type="text/javascript" src="js/vue.min.js"></script>
<script type="text/javascript" src="js/iview.min.js"></script>
<script src="js/mqtt.js"></script>
<script>
  const myVue = new Vue({
    el: '#app',
    data: {
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
          url: 'ws://test.ranye-iot.net:8080',
          // 需要修改
          topic: 'connect_all_esp8266_mqtt_led_topic',
          connectTimeout: 5000,
          clientId: 'connect_all_esp8266_mqtt_led_' + new Date().getTime(),
          clean: false,
          keepAliveInterval: 30
        },
      },
      bottomDesc: 'B站: 【不能不存在】智能物联系列'
    },
    methods: {
      /**
       * 开关灯动作
       */
      switchFun() {
        // 如果为关灯状态则开灯，否则相反
        let status = this.ledDevice.status === 0 ? 1 : 0;
        this.mqttPublish(status);
      },
      /**
       * 发送消息
       */
      mqttPublish(status) {
        // 向指定topic发送消息，topic要保持一致
        this.ledDevice.client.publish(this.ledDevice.options.topic, status.toString(), {
          qos: 1
        });
      },
      /**
       * mqtt初始化
       */
      mqttConf() {
        // 链接mqtt
        this.ledDevice.client = mqtt.connect(this.ledDevice.options.url, this.ledDevice.options);
        // 链接成功回调方法
        this.ledDevice.client.on('connect', function() {
          myVue.$Message.success('连接成功!');
          myVue.ledDevice.client.subscribe(myVue.ledDevice.options.topic, function(err) {
            if (!err) {
              console.log('订阅成功:', myVue.ledDevice.options.topic)
            } else {
              console.log('订阅失败:', myVue.ledDevice.options.topic, err)
            }
          })
        }).on('reconnect', function(error) {
          myVue.$Message.info('正在重连...');
        }).on('error', function(error) {
          myVue.$Message.error('连接失败!');
        }).on('end', function() {
          myVue.$Message.warn('连接断开!');
        }).on('message', function(topic, message) {
          // 接收到消息
          myVue.ledDevice.status = Number(message);
          myVue.ledDevice.descTextStyle = {
            color: myVue.ledDevice.color[Number(message)]
          };
        });
      }
    },
    mounted() {
      this.mqttConf();
    }
  });
</script>
</body>
</html>
