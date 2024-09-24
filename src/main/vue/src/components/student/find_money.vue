<template>

    <div>
      <h1>查询余额</h1>
      <router-link to="index">返回</router-link>
      <div>{{name}} 同学你好,你剩余的余额是 {{money}}</div>


    </div>
</template>

<script>

import axios from 'axios'

export default{

data(){
  return{
    id:"",
    name:"",
    money:""
  }
},

created(){
    this['id'] = sessionStorage.getItem("id");
    axios.get("money/find/" + this['id']).then((responds) => {
        console.log("请求成功");
        console.log(responds.data);
        this['name'] = responds.data.name;
        this['money'] = responds.data.money;
        

    } )
}
}

</script>
