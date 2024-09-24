<template>
<div>

  <!--导航栏 -->
  <el-menu default-active="1" class="el-menu-demo" mode="horizontal" @select="handleSelect">



  </el-menu>



  <!-- 表格 -->
  <!--
  <table>
    <tr>
    <th>编号</th>
    <th>下单人</th>
    <th>内容</th>
    <th>价格</th>
    <th>状态</th>
    </tr>

    <div v-if="list.length === 0">暂无数据</div>
    <tr v-for="i in list">
      <td>{{i.id}}</td>
      <td>{{i.stu_name}}</td>
      <td>{{i.content}}</td>
      <td>{{i.money}}</td>
      <td>{{i.state}}</td>
      <td v-if="i.state === '待支付'">
        <el-button @click="repay(i.id,i.money)" type="primary">重新支付</el-button>
        <el-button type="danger">取消订单</el-button>
      </td>
      <td v-if="i.state === '已支付'">
        <el-button @click="bill(i.id,'待取餐')" type="message">制作完成</el-button>
      </td>
      <td v-if="i.state === '待取餐'">
        <el-button @click="bill(i.id,'已完成',i.money)" type="success">订单完成</el-button>
      </td>


    </tr>
  </table>

   -->



  <el-table
      :data="list"

      style="width: 100%;font-size: 25px;color: black" table-layout='auto'
      >

    <el-table-column
        prop="id"
        label="编号"
        width="100">
    </el-table-column>
    <el-table-column
        prop="content"
        label="内容"
        width="500">
    </el-table-column>
    <el-table-column
        prop="money"
        label="价格"
        width="100">
    </el-table-column>





    <el-table-column
        prop="date"
        label="时间"
        width="300">
    </el-table-column>

    <el-table-column label="操作" width="180">
      <template slot-scope="scope">

        <td>
          <el-button type="danger" @click="del(scope.row.id)">删除订单</el-button>
        </td>


      </template>
    </el-table-column>

  </el-table>


  <!-- 分页 -->
  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-sizes="[1,2,5,10]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
  </el-pagination>



</div>
</template>

<script>
import axios from "axios"

export default {
  data() {
    return {
      list: [],
      state : null,
      page:1,
      size:5,
      total:0,

    }
  },

  created() {
    this.initWebSocket()
    this.get_bill(null,1)
  },

  methods: {
    initWebSocket(){//初始化weosocket(必须)
      const wsuri = "ws://192.168.0.187:8088/websocket";    //请根据实际项目需要进行修改
      this.websock = new WebSocket(wsuri);      //新建一个webstock对象
      this.websock.onmessage = this.websocketonmessage;
      this.websock.onopen = this.websocketonopen;
      this.websock.onerror = this.websocketonerror;
      this.websock.onclose = this.websocketclose;
      console.log(this.websock);
    },


    websocketonmessage(e) { //数据接收
      var data = JSON.parse(e.data)
      if (data.msg === "支付成功") {
        alert("用户支付成功")
        this.$router.go(0);
      }
    },

    bill(id, state,money) {
      console.log(money);
      let params = new URLSearchParams();
      params.append("id", id);
      params.append("state", state);
      if(state === "已完成") params.append("money",money);
      axios.post("food/change", params).then(res => {
        console.log(res)
        this.get_bill(null,1)
      })





    },
    name(con) {
      if (con != null) {
        console.log(con[0])
        return con[0] + "同学"
      }
    },
    repay(id, money) {
      let params = new URLSearchParams();
      params.append("id", id);
      params.append("money", money);

      axios.post("pay/pay", params).then(res => {
        console.log(res)

      })
    },


    get_bill(id,page){
      this.state = id;
      this.page = page;
      axios.get("bill/search",{params:{
          state:this.state,
          page:this.page,
          size:this.size
        }}).then(res=>{
          console.log(res.data.records)
          this.list = res.data.records
          this.total = res.data.total
      })


    },

    handleSizeChange(e){
      this.size = e;
      this.get_bill(this.state,this.page);
    },

    handleCurrentChange(e){
      this.page = e;
      this.get_bill(this.state,this.page);
    },

    del(id){
      this.$confirm('删除订单是否继续?', '删除无法撤回,请谨慎操作', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {


        axios.post('/bill/del/' + id).then((res) =>{
          console.log(res)
          this.get_bill(null,1)
        })



        this.$message({
          type: 'success',
          message: '删除成功!'
        });

      })
    },

    handleSelect(e){

      if(e === "1") this.get_bill(null,1)
      else if(e === "2") this.get_bill('待支付',1)
      else if(e === "3") this.get_bill('已支付',1)
      else if(e === "4") this.get_bill('待取餐',1)
      else if(e === "5") this.get_bill('已完成',1)
    }


  }
}
</script>

<style scoped>
cellStyle{
  font-size: 50px;
}
th,td{
  padding: 5px;
}
</style>
