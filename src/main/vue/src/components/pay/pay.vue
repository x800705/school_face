<template>
    <div>

        <input type="text" v-model="money">
        <button @click="submit">提交</button>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            money:"",
        }
    },
    methods:{
        submit(){
            axios.get("http://192.168.0.187:8088/pay/pay?money="+this.money).then((res) =>{
                console.log(res);
            })
        }
    }
}
</script>
