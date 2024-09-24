<template>
  <div>

    <!--导航栏 -->
    <!--
    <el-menu default-active="7" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <el-menu-item index='7'>7天数据</el-menu-item>
      <el-menu-item index='15'>15天数据</el-menu-item>
      <el-menu-item index='30'>30天数据</el-menu-item>
    </el-menu>
    -->

    <el-input type="month" autocomplete="off" v-model="time" style="width:300px;padding: 20px 0px"></el-input>
    <el-button @click="find()">提交</el-button>
    <div v-if="money.length === 0">暂无数据</div>
    <div id="myChart" style="width:600px;height: 600px;" v-else></div>


    <div style="float: left" >
      <table>
        <tr v-for="(i,index) in rank_list">
          <td>{{index + 1}}</td>
          <td>{{i.date}}</td>
          <td>{{i.money}}</td>
        </tr>
      </table>
    </div>




  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      list:[],
      date:[],
      money:[],
      myChart:"",
      rank_list:[],
      time:""
    }
  },
  created(){
    var date = new Date();
    var month = "";
    if(date.getMonth() + 1 < 9)  month = "0" + (date.getMonth() + 1)
    this.time = date.getFullYear() + "-" + month
    this.drawNewLine(this.time);

  },
  methods: {

    async drawLine(day){
      document.getElementById('myChart').removeAttribute('_echarts_instance_')
          this.list = [],
          this.date = [],
          this.money = [],
          this.myChart = []



      await axios.get("food/money?day=" + day).then(res =>{
        this.list = res.data;
        console.log(this.list)
        for(var i in this.list){
          this.money.push(this.list[i].money)
          this.date.push(this.list[i].date)
        }
        console.log(this.date);
        console.log(this.money);
      })



      // 基于准备好的dom，初始化echarts实例
      this.myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      this.myChart.setOption({
        title: { text: '收入管理',
          textStyle: {
            "fontSize": 30
          }},
        tooltip: {
          trigger: 'axis',

        },
        xAxis: {
          fontSize:'50px',
          type: 'category',
          data: this.date,
          axisLabel:{
            textStyle: {
              "fontSize": 20,
              "color":"black",
            }
          }

        },
        yAxis: {
          axisLabel:{
            textStyle: {
              "fontSize": 20,
              "color":"black",
            }
          }
        },

        series: [{
          smooth: true,
          name: '销售金额',
          data: this.money,
          type: 'line',




            lineStyle: {
              normal: {
                color: 'rgb(145,199,174)',
              }
            }

        }]
      });
    },

    async drawNewLine(date){
      document.getElementById('myChart')
          this.list = [],
          this.date = [],
          this.money = [],
          this.myChart = []



      await axios.get("food/new_money?date=" + date).then(res =>{
        this.list = res.data;
        console.log(this.list)
        for(var i in this.list){
          this.money.push(this.list[i].money)
          this.date.push(this.list[i].date)
        }
        console.log(this.date);
        console.log(this.money);
      })



      // 基于准备好的dom，初始化echarts实例
      this.myChart = this.$echarts.init(document.getElementById('myChart'))
      // 绘制图表
      this.myChart.setOption({
        title: { text: '收入管理',
          textStyle: {
            "fontSize": 30
          }},
        tooltip: {
          trigger: 'axis',

        },
        xAxis: {
          fontSize:'50px',
          type: 'category',
          data: this.date,
          axisLabel:{
            textStyle: {
              "fontSize": 20,
              "color":"black",
            }
          }

        },
        yAxis: {
          axisLabel:{
            textStyle: {
              "fontSize": 20,
              "color":"black",
            }
          }
        },

        series: [{
          smooth: true,
          name: '销售金额',
          data: this.money,
          type: 'line',




          lineStyle: {
            normal: {
              color: 'rgb(145,199,174)',
            }
          }

        }]
      });
    },

    handleSelect(e){
      if(e === "7") this.drawLine(7);
      else if(e === "15") this.drawLine(15);
      else if(e === "30") this.drawLine(30);
    },


    find(){
      this.drawNewLine(this.time)
    }
  }
}

</script>
