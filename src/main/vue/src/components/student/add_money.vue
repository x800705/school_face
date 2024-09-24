<template>

    <div>

      <h1>充值</h1>
      <router-link to="index">返回</router-link> <br>
      <input type="text" placeholder="请输入充值金额" v-model="money"> <br>
      <input type="button" @click="add()" value="提交">
      <div v-html="ali"></div>

    </div>


</template>


<script>
import axios from 'axios';

export default{
  data(){
    return{
      id:"",
      money:"",
      date:"",
      content:"充值",
      ali:"",
    }
  },

created(){
  this['id'] = sessionStorage.getItem("id");

  var date = new Date();
  var year = date.getFullYear();
  var month = date.getMonth() + 1;

  if(month < 10) month = "0" + month;
  var day = date.getDate();
  if(day < 10) day = "0" + day;
  var time = year + "-" + month + "-" + day;

  this['date'] = time;

},

methods:{
add(){
  let param = new URLSearchParams();
  param.append("id",this['id']);
  param.append("money",this['money']);
  param.append("date",this['date']);
  param.append("content","充值");
  axios.get("student/ali_pay").then(resp=>{
    const div = document.createElement("div");
    div.innerHTML = resp.data; // data就是接口返回的form 表单字符串
    document.body.appendChild(div);
    document.forms[0].setAttribute("target", "_blank"); // 新开窗口跳转
    document.forms[0].submit();



  })

  axios.post("money/add_stu",param).then((response) =>{
    if(response.data == 1){
      alert("充值" + this['money'] + "元成功");

    }
    else{
      alert("充值失败，请稍后再试");
    }
  })
}
}


}

</script>
